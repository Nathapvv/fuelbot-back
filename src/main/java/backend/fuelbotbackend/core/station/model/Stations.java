package backend.fuelbotbackend.core.station.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class Stations {

    private long id;
    private String type;
    private String name;

    @JsonProperty("Brand")
    private Brand brand;

    @JsonProperty("Address")
    private Address address;

    @JsonProperty("Coordinates")
    private Coordinates coordinates;

    private int distance;

    @JsonProperty("Distance")
    private DistanceInfo distanceInfo;
     
    @JsonProperty("Fuels")
    private List<Fuel> fuels;
}
