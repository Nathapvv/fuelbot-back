package backend.fuelbotbackend.business.order.model;

import backend.fuelbotbackend.business.user.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "`order`")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCommande;

    private LocalDateTime orderDate = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "id_utilisateur", nullable = false)
    private User user;

    private double fuelQuantity;

    private String fuelType;

    private String totalPrice;

    private String orderStatus;

    private String station;
}
