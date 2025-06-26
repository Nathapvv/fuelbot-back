package backend.fuelbotbackend.business.wallet.service;

import backend.fuelbotbackend.business.wallet.dto.WalletSoldeResponse;
import jakarta.transaction.Transactional;

public interface WalletService {
    @Transactional
    void deposit(Long userId, double amount);

    @Transactional
    WalletSoldeResponse getSoldeDto(Long userId);
}
