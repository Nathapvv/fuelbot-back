package backend.fuelbotbackend.core.station.service.impl;

import backend.fuelbotbackend.core.station.api.StationsFeignClient;
import backend.fuelbotbackend.core.station.dto.LocationRequest;
import backend.fuelbotbackend.core.station.dto.LocationResponse;
import backend.fuelbotbackend.core.station.dto.StationDetailsResponse;
import backend.fuelbotbackend.core.station.dto.StationSummaryResponse;
import backend.fuelbotbackend.core.station.model.Stations;
import backend.fuelbotbackend.core.station.service.StationService;
import lombok.RequiredArgsConstructor; 
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StationServiceImpl implements StationService {

 private final StationsFeignClient stationsFeignClient;

    @Value("${opencage.api.key}")
    private String opencageKey;

    @Override
    public List<LocationResponse> getNearbyStations(LocationRequest locationRequest) {
        String latlon = locationRequest.getLatitude() + "," + locationRequest.getLongitude();
        List<Stations> stations = stationsFeignClient.getStations(latlon); 

        return stations.stream()
                .map(station -> new LocationResponse(
                        station.getId(),
                        station.getName(),
                        station.getBrand().getName(),
                        station.getAddress().getCityLine(),
                        station.getDistanceInfo().getText(),
                        Double.parseDouble(station.getCoordinates().getLatitude()),
                        Double.parseDouble(station.getCoordinates().getLongitude()), stationsFeignClient.getStationsDetails(station.getId()).getFuels()
                ))
                .collect(Collectors.toList());
    }
    @Override
    public StationSummaryResponse getStationDetails(long stationId) {
        StationDetailsResponse details = stationsFeignClient.getStationsDetails(stationId);

        return new StationSummaryResponse(
                details.getName(),
                details.getBrand().getName(),
                details.getAddress().getCityLine(),
                null,
                Double.parseDouble(details.getCoordinates().getLatitude()),
                Double.parseDouble(details.getCoordinates().getLongitude()),
                details.getFuels()
        );
    }

    private String mapToApiFuelName(String userFuelType) {
    return switch (userFuelType.toLowerCase()) {
        case "diesel", "gazole", "b7" -> "Gazole";
        case "e85", "ethanol" -> "Super Ethanol E85";
        case "95", "sp95", "e10", "sans plomb 95", "sp95 e10" -> "Super Sans Plomb 95 E10";
        case "98", "sp98", "sans plomb 98" -> "Super Sans Plomb 98";
        default -> userFuelType;  
    };
}

public List<LocationResponse> getStationsByAddress(String address, String fuelType) {
    try {
        String url = "https://api.opencagedata.com/geocode/v1/json?q=" + URLEncoder.encode(address, StandardCharsets.UTF_8) + "&key=" + opencageKey;
        RestTemplate restTemplate = new RestTemplate();
        String json = restTemplate.getForObject(url, String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);
        JsonNode results = root.get("results");

        if (results == null || !results.isArray() || results.size() == 0) {
            return List.of();
        }

        JsonNode geometry = results.get(0).get("geometry");
        double lat = geometry.get("lat").asDouble();
        double lng = geometry.get("lng").asDouble();

        LocationRequest locReq = new LocationRequest(lat, lng);
        List<LocationResponse> stations = getNearbyStations(locReq);
 
        if (fuelType == null || fuelType.isBlank()) {
            return stations;
        }

        String expectedFuel = mapToApiFuelName(fuelType);
 
        return stations.stream()
    .filter(st -> st.getFuels() != null &&
                  st.getFuels().stream().anyMatch(fuel ->
                      fuel.getName().equalsIgnoreCase(expectedFuel) && fuel.isAvailable()
                  ))
    .collect(Collectors.toList());

    } catch (Exception e) {
        e.printStackTrace();
        return List.of();
    }
}

}
