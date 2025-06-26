package backend.fuelbotbackend.core.station.dto;

import java.util.List; 

import backend.fuelbotbackend.core.station.model.Fuel;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LocationResponse {
    private long id;
    private String name;
    private String brand;
    private String city;
    private String distance;
    private double latitude;
    private double longitude;  
    private List<Fuel> fuels;
}
