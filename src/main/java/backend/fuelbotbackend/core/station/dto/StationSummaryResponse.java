package backend.fuelbotbackend.core.station.dto;

import backend.fuelbotbackend.core.station.model.Fuel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StationSummaryResponse {
    private String name;
    private String brand;
    private String city;
    private String distance;
    private double latitude;
    private double longitude;
    private List<Fuel> fuels;
}
