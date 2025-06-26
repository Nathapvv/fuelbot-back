package backend.fuelbotbackend.business.user.service;

import backend.fuelbotbackend.business.user.dto.UserResponse;
import backend.fuelbotbackend.business.user.dto.UserUpdateRequest;
import backend.fuelbotbackend.business.user.dto.UserUpdateResponse;
import backend.fuelbotbackend.business.user.model.User;
import org.springframework.transaction.annotation.Transactional;

public interface UserBusinessService {

    User createAccount(String nom ,String prenom,String email, String motDePasse);
    UserResponse login(String email, String motDePasse);
    
    @Transactional
    String resetPassword(String email);

    @Transactional
    UserUpdateResponse updateUtilisateur(String email, UserUpdateRequest request);
}
