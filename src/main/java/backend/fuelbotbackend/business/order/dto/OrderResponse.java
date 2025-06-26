package backend.fuelbotbackend.business.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class  OrderResponse {
    private Long idCommande;
    private LocalDateTime orderDate;
    private String userName;
    private String station;
    private String fuelType;
    private double fuelQuantity;
    private String totalPrice;
    private String orderStatus;
}
