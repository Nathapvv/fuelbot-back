package backend.fuelbotbackend.business.wallet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class WalletSoldeResponse {
    private Long userId;
    private double solde;
}
