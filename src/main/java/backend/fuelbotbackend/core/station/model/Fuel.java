package backend.fuelbotbackend.core.station.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Fuel {
    private int id;
    private String name;
    private String short_name;
    private String picto;
    @JsonProperty("Update")
    private Update update;
    private boolean available;
    @JsonProperty("Price")
    private Price price;
}
