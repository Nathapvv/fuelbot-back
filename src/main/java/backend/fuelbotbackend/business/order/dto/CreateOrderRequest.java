package backend.fuelbotbackend.business.order.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrderRequest {
    private Long utilisateurId;
    private Long stationId;
    private String fuelType;
    private double fuelQuantity;
}
