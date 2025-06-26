package backend.fuelbotbackend.business.user.controller;

import backend.fuelbotbackend.business.user.dto.UserResponse;
import backend.fuelbotbackend.business.user.dto.UserUpdateRequest;
import backend.fuelbotbackend.business.user.dto.UserUpdateResponse;
import backend.fuelbotbackend.business.user.model.User;
import backend.fuelbotbackend.business.user.service.UserBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/utilisateur")
public class UserController {

    @Autowired
    private UserBusinessService userBusinessService;

    @PostMapping("/create")
    public ResponseEntity<?> createAccount(
        @RequestParam String nom,@RequestParam String prenom,@RequestParam String email,
                                           @RequestParam String motDePasse) {
        try {
            User newUser = userBusinessService.createAccount(nom,prenom,email, motDePasse);
            return ResponseEntity.ok(newUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String motDePasse) {
        try {
            UserResponse userResponse = userBusinessService.login(email, motDePasse);
            return ResponseEntity.ok(userResponse);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update/{email}")
    public ResponseEntity<?> updateUtilisateur(@PathVariable String email,
                                               @RequestBody UserUpdateRequest request) {
        try {
            UserUpdateResponse updatedUser = userBusinessService.updateUtilisateur(email, request);
            return ResponseEntity.ok(updatedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String email) {
        String newPassword = userBusinessService.resetPassword(email);
        return ResponseEntity.ok("Nouveau mot de passe : " + newPassword);
    }
}
