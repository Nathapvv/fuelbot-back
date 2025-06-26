package backend.fuelbotbackend.business.user.service.impl;

import backend.fuelbotbackend.business.user.dto.UserResponse;
import backend.fuelbotbackend.business.user.dto.UserUpdateResponse;
import backend.fuelbotbackend.business.user.model.User;
import backend.fuelbotbackend.business.user.repository.UserRepository;
import backend.fuelbotbackend.business.user.service.UserBusinessService;
import backend.fuelbotbackend.business.user.dto.UserUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserBusinessServiceImpl implements UserBusinessService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User createAccount(String nom,String prenom,String email, String motDePasse) {
        // Vérifier si l'email est déjà utilisé
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("L'email est déjà utilisé !");
        }

        // Hasher le mot de passe pour la sécurité
        String hashedPassword = passwordEncoder.encode(motDePasse);

        // Créer un nouvel utilisateur
        User user = new User();
        user.setNom(nom);
        user.setPrenom(prenom);
        user.setEmail(email);
        user.setMotDePasse(hashedPassword);

        // Sauvegarder dans la base
        return userRepository.save(user);
    }

    @Override
    public UserResponse login(String email, String motDePasse) {
        Optional<User> utilisateurOptional = userRepository.findByEmail(email);

        if (utilisateurOptional.isEmpty()) {
            throw new IllegalArgumentException("Email incorrect ou utilisateur non trouvé.");
        }

        User user = utilisateurOptional.get();

        if (!passwordEncoder.matches(motDePasse, user.getMotDePasse())) {
            throw new IllegalArgumentException("Mot de passe incorrect.");
        }

        UserResponse response = new UserResponse();
response.setIdUtilisateur(user.getIdUtilisateur());
response.setNom(user.getNom());
response.setPrenom(user.getPrenom());
response.setEmail(user.getEmail());
response.setDateNaissance(user.getDateNaissance());
response.setNumeroTelephone(user.getNumeroTelephone()); 
return response;

    }

    @Transactional
    @Override
    public String resetPassword(String email) {
        Optional<User> utilisateurOptional = userRepository.findByEmail(email);

        if (utilisateurOptional.isEmpty()) {
            throw new IllegalArgumentException("Aucun utilisateur trouvé avec cet email.");
        }

        User user = utilisateurOptional.get();

        String motDePasseTemporaire = generateTemporaryPassword();

        String hashedPassword = passwordEncoder.encode(motDePasseTemporaire);

        user.setMotDePasse(hashedPassword);
        userRepository.save(user);

        return motDePasseTemporaire;
    }

    private String generateTemporaryPassword() {
        int length = 10;
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int idx = (int) (Math.random() * chars.length());
            sb.append(chars.charAt(idx));
        }
        return sb.toString();
    }

    @Transactional
    @Override
    public UserUpdateResponse updateUtilisateur(String email, UserUpdateRequest request) {
        Optional<User> utilisateurOpt = userRepository.findByEmail(email);
        if (utilisateurOpt.isEmpty()) {
            throw new IllegalArgumentException("Utilisateur non trouvé.");
        }

        User user = utilisateurOpt.get();

        // Mise à jour de l'email
        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
            if (existingUser.isPresent() && !existingUser.get().getEmail().equals(email)) {
                throw new IllegalArgumentException("Le nouvel email est déjà utilisé par un autre utilisateur !");
            }
            user.setEmail(request.getEmail());
        }

        // Mise à jour du mot de passe
        if (request.getNouveauMotDePasse() != null && !request.getNouveauMotDePasse().isBlank()) {
            String hashed = passwordEncoder.encode(request.getNouveauMotDePasse());
            user.setMotDePasse(hashed);
        }

        // Mise à jour du nom
        if (request.getNom() != null && !request.getNom().isBlank()) {
            user.setNom(request.getNom());
        }

        // Mise à jour du prénom
        if (request.getPrenom() != null && !request.getPrenom().isBlank()) {
            user.setPrenom(request.getPrenom());
        }

        // Mise à jour du numéro de téléphone
        if (request.getNumeroTelephone() != null) {
            user.setNumeroTelephone(request.getNumeroTelephone());
        }

        // Mise à jour de la date de naissance
        if (request.getDateNaissance() != null) {
            user.setDateNaissance(request.getDateNaissance());
        }

        return toDto(userRepository.save(user));
    }

    private UserUpdateResponse toDto(User user) {
        return new UserUpdateResponse(
                user.getIdUtilisateur(),
                user.getNom(),
                user.getPrenom(),
                user.getEmail(),
                user.getNumeroTelephone(),
                user.getDateNaissance()
        );
    }
}
