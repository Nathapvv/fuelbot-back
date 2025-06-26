package backend.fuelbotbackend.business.order.repository;

 
import backend.fuelbotbackend.business.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserIdUtilisateur(Long idUtilisateur);
}
