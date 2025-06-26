package backend.fuelbotbackend.business.wallet.model;

import backend.fuelbotbackend.business.user.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idWallet;

    private double balance; // Montant disponible en €

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "idUtilisateur")
    private User user;
}
