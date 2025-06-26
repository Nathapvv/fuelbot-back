package backend.fuelbotbackend.business.user.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserUpdateRequest {

    private String email;
    private String nouveauMotDePasse;
    private String nom;
    private String prenom;
    private String numeroTelephone;
    private Date dateNaissance;
}
