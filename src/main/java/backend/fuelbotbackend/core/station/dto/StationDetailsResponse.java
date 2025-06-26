package backend.fuelbotbackend.core.station.dto;

import backend.fuelbotbackend.core.station.model.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StationDetailsResponse {
    private long id;
    private String type;
    private String name;
    @JsonProperty("Brand")
    private Brand brand;
    @JsonProperty("Address")
    private Address address;
    @JsonProperty("Coordinates")
    private Coordinates coordinates;
    @JsonProperty("Hours")
    private Hours hours;
    @JsonProperty("Services")
    private List<String> services;
    @JsonProperty("Fuels")
    private List<Fuel> fuels;
    private Update lastUpdate;
}
