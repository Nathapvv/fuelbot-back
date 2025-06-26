package backend.fuelbotbackend.core.station.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address {
    @JsonProperty("street_line")
    private String streetLine;

    @JsonProperty("city_line")
    private String cityLine;
}
