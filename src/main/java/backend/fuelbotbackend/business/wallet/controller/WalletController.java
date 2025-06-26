package backend.fuelbotbackend.business.wallet.controller;

import backend.fuelbotbackend.business.common.dto.ErrorResponse;
import backend.fuelbotbackend.business.wallet.dto.WalletDepositRequest;
import backend.fuelbotbackend.business.wallet.dto.WalletSoldeResponse;
import backend.fuelbotbackend.business.wallet.service.WalletService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;

@RestController
@RequestMapping("/api/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestBody WalletDepositRequest request, HttpServletRequest httpRequest) {
        try {
            walletService.deposit(request.getUserId(), request.getAmount());
            return ResponseEntity.ok(Collections.singletonMap("message", "Dépôt effectué avec succès."));
        } catch (IllegalArgumentException e) {
            ErrorResponse error = new ErrorResponse(
                    LocalDateTime.now(),
                    HttpStatus.BAD_REQUEST.value(),
                    "Dépôt impossible",
                    e.getMessage(),
                    httpRequest.getRequestURI()
            );
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/solde")
    public ResponseEntity<?> getSolde(@RequestParam Long userId, HttpServletRequest request) {
        try {
            WalletSoldeResponse solde = walletService.getSoldeDto(userId);
            return ResponseEntity.ok(solde);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ErrorResponse(
                            LocalDateTime.now(),
                            HttpStatus.NOT_FOUND.value(),
                            "Solde indisponible",
                            e.getMessage(),
                            request.getRequestURI()
                    )
            );
        }
    }
}
