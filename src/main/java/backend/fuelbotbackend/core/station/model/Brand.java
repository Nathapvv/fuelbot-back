package backend.fuelbotbackend.core.station.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Brand {
    private int id;
    private String name;

    @JsonProperty("short_name")
    private String shortName;

    @JsonProperty("nb_stations")
    private int nbStations;
}
