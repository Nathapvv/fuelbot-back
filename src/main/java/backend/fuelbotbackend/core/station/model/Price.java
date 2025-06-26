package backend.fuelbotbackend.core.station.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Price {
    private double value;
    private String currency;
    private String text;
}
