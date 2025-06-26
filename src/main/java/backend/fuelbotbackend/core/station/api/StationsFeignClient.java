package backend.fuelbotbackend.core.station.api;

import backend.fuelbotbackend.core.station.dto.StationDetailsResponse;
import backend.fuelbotbackend.core.station.model.Stations;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "carburantClient", url = "${carburant.api.url}")
public interface StationsFeignClient {

    @GetMapping("/stations/around/{latlon}")
    List<Stations> getStations(@PathVariable("latlon") String latlon);

    @GetMapping("/station/{id}")
    StationDetailsResponse getStationsDetails(@PathVariable("id") long stationId);
}

