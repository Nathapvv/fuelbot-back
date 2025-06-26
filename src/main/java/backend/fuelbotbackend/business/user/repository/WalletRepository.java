package backend.fuelbotbackend.business.user.repository;

import backend.fuelbotbackend.business.wallet.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Wallet findByUserIdUtilisateur(Long idUtilisateur);
}
