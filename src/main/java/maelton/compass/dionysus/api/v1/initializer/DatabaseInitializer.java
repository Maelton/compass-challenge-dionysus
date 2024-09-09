package maelton.compass.dionysus.api.v1.initializer;

import maelton.compass.dionysus.api.v1.model.dto.sale.SaleRequestDTO;
import maelton.compass.dionysus.api.v1.model.dto.sale.SaleResponseDTO;
import maelton.compass.dionysus.api.v1.model.dto.user.UserRequestDTO;
import maelton.compass.dionysus.api.v1.model.dto.user.UserResponseDTO;
import maelton.compass.dionysus.api.v1.model.dto.wine.WineRequestDTO;
import maelton.compass.dionysus.api.v1.model.dto.wine.WineResponseDTO;
import maelton.compass.dionysus.api.v1.model.dto.wine_model.WineModelRequestDTO;
import maelton.compass.dionysus.api.v1.model.dto.wine_model.WineModelResponseDTO;
import maelton.compass.dionysus.api.v1.model.enums.ProductStatus;
import maelton.compass.dionysus.api.v1.model.enums.UserRole;
import maelton.compass.dionysus.api.v1.model.enums.WineType;
import maelton.compass.dionysus.api.v1.repository.WineModelRepository;
import maelton.compass.dionysus.api.v1.service.SaleService;
import maelton.compass.dionysus.api.v1.service.UserService;
import maelton.compass.dionysus.api.v1.service.WineModelService;
import maelton.compass.dionysus.api.v1.service.WineService;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

@Component
public class DatabaseInitializer {

    private final UserService userService;
    private final WineModelService wineModelService;
    private final WineService wineService;
    private final SaleService saleService;
    private final WineModelRepository wineModelRepository;

    public DatabaseInitializer(UserService userService, WineModelService wineModelService, WineService wineService, SaleService saleService, WineModelRepository wineModelRepository) {
        this.userService = userService;
        this.wineModelService = wineModelService;
        this.wineService =  wineService;
        this.saleService = saleService;
        this.wineModelRepository = wineModelRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initializeDatabase() {
        try {
            UserResponseDTO user1 = createDefaultUser("Admin", LocalDate.of(2000, 1, 1), "admin@dionysus.com", "admin", UserRole.ADMIN);
            UserResponseDTO user2 = createDefaultUser("User", LocalDate.of(2000, 1, 1), "user@dionysus.com", "user", UserRole.USER);
            UserResponseDTO user3 = createDefaultUser("Maelton Lima", LocalDate.of(2002, 11, 8), "maelton.compass@gmail.com", "maeltonlima", UserRole.USER);

            WineModelResponseDTO wineModel1 = createDefaultWineModel("Bodega Catena Zapata", "Malbec", 750, 13.5, WineType.RED, 120.00);
            WineModelResponseDTO wineModel2 = createDefaultWineModel("Château Margaux", "Pavillon Rouge", 750, 14.0, WineType.RED, 2500.00);

            WineResponseDTO wine1 = createDefaultWine(wineModel1.id(), 135.00, ProductStatus.AVAILABLE);
            WineResponseDTO wine2 = createDefaultWine(wineModel1.id(), 135.00, ProductStatus.UNAVAILABLE);
            WineResponseDTO wine3 = createDefaultWine(wineModel2.id(), 2600.00, ProductStatus.AVAILABLE);
            WineResponseDTO wine4 = createDefaultWine(wineModel2.id(), 2600.00, ProductStatus.UNAVAILABLE);

            SaleResponseDTO sale1 = createDefaultSale(user1.id(), wine1.id());
            SaleResponseDTO sale2 = createDefaultSale(user2.id(), wine3.id());
        } catch (Exception e) {
            return;
        }
    }

    public UserResponseDTO createDefaultUser(String name, LocalDate birthDate, String email, String password, UserRole role) {
        try {
            return userService.createUser(new UserRequestDTO(name, birthDate, email, password, role));
        } catch (Exception e) {
            return null;
        }
    }

    public WineModelResponseDTO createDefaultWineModel(String brand, String name, double volume, double abv, WineType type, double recommendedPrice) {
        if(!wineModelRepository.existsByBrandAndNameAndVolumeAndAbvAndType(brand, name, volume, abv, type))
            return wineModelService.createWineModel(new WineModelRequestDTO(brand, name, volume, abv, type, recommendedPrice));
        return null;
    }

    public WineResponseDTO createDefaultWine(Long modelId, Double price, ProductStatus status) {
        try {
            return wineService.createWine(new WineRequestDTO(modelId, price, status));
        } catch (Exception e) {
            return null;
        }
    }

    public SaleResponseDTO createDefaultSale(UUID customerId, UUID productId) {
        try {
            return saleService.createSale(new SaleRequestDTO(customerId, productId));
        } catch (Exception e) {
            return null;
        }
    }
}