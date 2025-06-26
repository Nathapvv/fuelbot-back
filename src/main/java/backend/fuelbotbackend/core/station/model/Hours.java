package backend.fuelbotbackend.core.station.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Hours {
    private boolean automate_24_7;
    private List<Object> Days;
}
