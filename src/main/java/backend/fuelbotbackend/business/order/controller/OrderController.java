package backend.fuelbotbackend.business.order.controller;

import backend.fuelbotbackend.business.common.dto.ErrorResponse;
import backend.fuelbotbackend.business.order.dto.CreateOrderRequest;
import backend.fuelbotbackend.business.order.dto.CreateOrderResponse;
import backend.fuelbotbackend.business.order.dto.OrderResponse;
import backend.fuelbotbackend.business.order.model.Order;
import backend.fuelbotbackend.business.order.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/createOrder")
    public ResponseEntity<?> createCommande(@RequestBody CreateOrderRequest request,
                                            HttpServletRequest httpRequest) {
        try {
            CreateOrderResponse response = orderService.createCommande(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            ErrorResponse error = new ErrorResponse(
                    LocalDateTime.now(),
                    HttpStatus.BAD_REQUEST.value(),
                    "Bad Request",
                    e.getMessage(),
                    httpRequest.getRequestURI()
            );
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PatchMapping("/validateOrder")
    public ResponseEntity<?> validerCommande(@RequestParam Long id, HttpServletRequest request) {
        try {
            orderService.validerCommande(id);
            return ResponseEntity.ok("Commande validée avec succès.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ErrorResponse(
                            LocalDateTime.now(),
                            HttpStatus.BAD_REQUEST.value(),
                            "Erreur lors de la validation",
                            e.getMessage(),
                            request.getRequestURI()
                    )
            );
        }
    }

    @GetMapping("/user")
public ResponseEntity<?> getOrdersByUser(@RequestParam Long userId, HttpServletRequest request) {
    try {
        List<OrderResponse> orders = orderService.getOrdersByUserIdUtilisateur(userId);
        return ResponseEntity.ok(orders);
    } catch (Exception e) {
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erreur serveur",
                "Impossible de récupérer les commandes de l'utilisateur",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}

}
