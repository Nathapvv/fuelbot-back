package backend.fuelbotbackend.business.station.controller;

import backend.fuelbotbackend.core.station.dto.LocationRequest;
import backend.fuelbotbackend.core.station.dto.LocationResponse;
import backend.fuelbotbackend.core.station.dto.StationSummaryResponse;
import backend.fuelbotbackend.core.station.service.StationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stations")
@RequiredArgsConstructor
public class StationController {

    private final StationService stationService;
@GetMapping("/search")
public List<LocationResponse> searchStationsNearAddress(@RequestParam String query,
                                                         @RequestParam(required = false) String fuelType) {
    return stationService.getStationsByAddress(query, fuelType);
}

    @GetMapping("/nearbyStations")
    public List<LocationResponse> getNearbyStations(@RequestParam double lat,
                                                    @RequestParam double lon) {
        LocationRequest locationRequest = new LocationRequest(lat, lon);
        return stationService.getNearbyStations(locationRequest);
    }

    @GetMapping("/stationDetails")
    public StationSummaryResponse getStationById(@RequestParam long id) {
        return stationService.getStationDetails(id);
    }
}