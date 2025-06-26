package backend.fuelbotbackend.core.station.service;

import backend.fuelbotbackend.core.station.dto.LocationRequest;
import backend.fuelbotbackend.core.station.dto.LocationResponse;
import backend.fuelbotbackend.core.station.dto.StationSummaryResponse;

import java.util.List;

public interface StationService {
    List<LocationResponse> getNearbyStations(LocationRequest locationRequest);

    StationSummaryResponse getStationDetails(long stationId);
    List<LocationResponse> getStationsByAddress(String address, String fuelType);
}
