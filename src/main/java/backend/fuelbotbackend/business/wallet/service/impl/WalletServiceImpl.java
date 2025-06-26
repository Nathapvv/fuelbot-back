package backend.fuelbotbackend.business.wallet.service.impl;

import backend.fuelbotbackend.business.user.model.User;
import backend.fuelbotbackend.business.user.repository.UserRepository;
import backend.fuelbotbackend.business.user.repository.WalletRepository;
import backend.fuelbotbackend.business.wallet.dto.WalletSoldeResponse;
import backend.fuelbotbackend.business.wallet.model.Wallet;
import backend.fuelbotbackend.business.wallet.service.WalletService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public void deposit(Long userId, double amount) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé"));

        Wallet wallet = walletRepository.findByUserIdUtilisateur(userId);

        if (wallet == null) {
            wallet = new Wallet();
            wallet.setUser(user);
            wallet.setBalance(amount);
        } else {
            wallet.setBalance(wallet.getBalance() + amount);
        }

        walletRepository.save(wallet);
    }

    @Transactional
    @Override
    public WalletSoldeResponse getSoldeDto(Long userId) {
        Wallet wallet = walletRepository.findByUserIdUtilisateur(userId);

        if (wallet == null) {
            throw new IllegalArgumentException("Aucun portefeuille trouvé pour cet utilisateur.");
        }

        return new WalletSoldeResponse(userId, wallet.getBalance());
    }
}
