package backend.fuelbotbackend.core.station.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LocationRequest {
    private double latitude;
    private double longitude;
}

