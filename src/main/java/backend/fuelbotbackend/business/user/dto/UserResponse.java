package backend.fuelbotbackend.business.user.dto;

import backend.fuelbotbackend.business.order.model.Order;
import backend.fuelbotbackend.business.wallet.model.Wallet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long idUtilisateur;
    private String nom;
    private String prenom;
    private String email;
    private Date dateNaissance;
    private String numeroTelephone; 
}
