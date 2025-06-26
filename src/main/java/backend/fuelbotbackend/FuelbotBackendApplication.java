package backend.fuelbotbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@SpringBootApplication
@EnableFeignClients
public class FuelbotBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(FuelbotBackendApplication.class, args);
    }

    // Configuration globale de CORS
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:4200")); // Autoriser Angular
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Méthodes autorisées
        config.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept")); // Headers autorisés
        config.setAllowCredentials(true); // Permettre l'envoi de cookies (si nécessaire)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // Appliquer à toutes les routes
        return new CorsFilter(source);
    }
}
