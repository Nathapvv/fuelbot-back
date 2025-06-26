package backend.fuelbotbackend.business.order.service.impl;

import backend.fuelbotbackend.business.order.dto.CreateOrderRequest;
import backend.fuelbotbackend.business.order.dto.CreateOrderResponse;
import backend.fuelbotbackend.business.order.dto.OrderResponse;
import backend.fuelbotbackend.business.order.model.Order;
import backend.fuelbotbackend.business.order.repository.OrderRepository;
import backend.fuelbotbackend.business.order.service.OrderService;
import backend.fuelbotbackend.business.user.model.User;
import backend.fuelbotbackend.business.wallet.model.Wallet;
import backend.fuelbotbackend.business.wallet.service.WalletService;
import backend.fuelbotbackend.business.user.repository.UserRepository;
import backend.fuelbotbackend.business.user.repository.WalletRepository;
import backend.fuelbotbackend.core.station.dto.StationSummaryResponse;
import backend.fuelbotbackend.core.station.model.Fuel;
import backend.fuelbotbackend.core.station.service.StationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final UserRepository userRepository;

    private final OrderRepository orderRepository;

    private final WalletRepository walletRepository;

    private final StationService stationService;

    private final WalletService walletService;

private String mapToApiFuelName(String userFuelType) {
    return switch (userFuelType.toLowerCase()) {
        case "diesel", "gazole", "b7" -> "Gazole";
        case "e85", "ethanol" -> "Super Ethanol E85";
        case "95", "sp95", "e10", "sans plomb 95", "sp95 e10" -> "Super Sans Plomb 95 E10";
        case "98", "sp98", "sans plomb 98" -> "Super Sans Plomb 98";
        default -> userFuelType;  
    };
}

   @Override
    @Transactional
    public CreateOrderResponse createCommande(CreateOrderRequest request) {
        User user = userRepository.findById(request.getUtilisateurId())
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé"));


        StationSummaryResponse details = stationService.getStationDetails(request.getStationId());

 
        String expectedName = mapToApiFuelName(request.getFuelType());

        Fuel fuel = details.getFuels().stream()
                .filter(f -> f.getName().equalsIgnoreCase(expectedName) && f.isAvailable())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Carburant non disponible"));

        double prixUnitaire = fuel.getPrice().getValue();
        double total = prixUnitaire * request.getFuelQuantity();

    
        double soldeActuel = walletService.getSoldeDto(user.getIdUtilisateur()).getSolde();
        if (soldeActuel < total) {
            throw new IllegalArgumentException("Solde insuffisant pour cette commande");
        }

        walletService.deposit(user.getIdUtilisateur(), -total);  


        Order order = new Order();
        order.setUser(user);
        order.setFuelType(fuel.getName());
        order.setFuelQuantity(request.getFuelQuantity());
        order.setTotalPrice(String.format("%.2f €", total));
        order.setOrderStatus("EN_ATTENTE");
        order.setStation(details.getName());

        Order saved = orderRepository.save(order);

        return new CreateOrderResponse(
                saved.getIdCommande(),
                saved.getOrderDate(),
                user.getNom(),
                saved.getStation(),
                saved.getFuelType(),
                saved.getFuelQuantity(),
                saved.getTotalPrice(),
                saved.getOrderStatus()
        );
    }

    @Transactional
    @Override
    public void validerCommande(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Commande non trouvée"));

        if ("VALIDÉE".equalsIgnoreCase(order.getOrderStatus())) {
            throw new IllegalArgumentException("La commande est déjà validée");
        }

        Wallet wallet = walletRepository.findByUserIdUtilisateur(order.getUser().getIdUtilisateur());
        if (wallet == null) {
            throw new IllegalArgumentException("Aucun portefeuille associé à cet utilisateur");
        }

        double montant = Double.parseDouble(order.getTotalPrice().replace(" €", "").replace(",", "."));

        if (wallet.getBalance() < montant) {
            throw new IllegalArgumentException("Fonds insuffisants pour valider la commande");
        }

        wallet.setBalance(wallet.getBalance() - montant);
        order.setOrderStatus("VALIDÉE");

        walletRepository.save(wallet);
        orderRepository.save(order);
    }

    @Override
    public List<OrderResponse> getOrdersByUserIdUtilisateur(Long userId) { 
        List<Order> orders = orderRepository.findByUserIdUtilisateur(userId);
    return orders.stream()
    .map(this::toOrderResponse)
    .toList(); // Java 16+ (ou use .collect(Collectors.toList()))
    }

    private OrderResponse toOrderResponse(Order order) {
        OrderResponse dto = new OrderResponse();
        dto.setIdCommande(order.getIdCommande());
        dto.setStation(order.getStation());
        dto.setFuelType(order.getFuelType());
        dto.setFuelQuantity(order.getFuelQuantity());
        dto.setTotalPrice(order.getTotalPrice());
        dto.setOrderStatus(order.getOrderStatus());
        dto.setOrderDate(order.getOrderDate());
        return dto;
    }
    
}
