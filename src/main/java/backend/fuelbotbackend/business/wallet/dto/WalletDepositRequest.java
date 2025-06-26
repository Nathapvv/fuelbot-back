package backend.fuelbotbackend.business.wallet.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WalletDepositRequest {
    private Long userId;
    private double amount;
}
