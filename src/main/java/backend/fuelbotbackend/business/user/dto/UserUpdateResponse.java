package backend.fuelbotbackend.business.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class UserUpdateResponse {
    private Long idUtilisateur;
    private String nom;
    private String prenom;
    private String email;
    private String numeroTelephone;
    private Date dateNaissance;
}
