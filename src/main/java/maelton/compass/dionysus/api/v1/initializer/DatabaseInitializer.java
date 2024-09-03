package maelton.compass.dionysus.api.v1.initializer;

import maelton.compass.dionysus.api.v1.model.dto.user.UserRequestDTO;
import maelton.compass.dionysus.api.v1.model.dto.wine.WineRequestDTO;
import maelton.compass.dionysus.api.v1.model.dto.wine_model.WineModelRequestDTO;
import maelton.compass.dionysus.api.v1.model.dto.wine_model.WineModelResponseDTO;
import maelton.compass.dionysus.api.v1.model.enums.ProductStatus;
import maelton.compass.dionysus.api.v1.model.enums.UserRole;
import maelton.compass.dionysus.api.v1.model.enums.WineType;
import maelton.compass.dionysus.api.v1.service.UserService;
import maelton.compass.dionysus.api.v1.service.WineModelService;
import maelton.compass.dionysus.api.v1.service.WineService;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DatabaseInitializer {

    private final UserService userService;
    private final WineModelService wineModelService;
    private final WineService wineService;
    public DatabaseInitializer(UserService userService, WineModelService wineModelService, WineService wineService) {
        this.userService = userService;
        this.wineModelService = wineModelService;
        this.wineService =  wineService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initializeDatabase() {
        createDefaultUser("Admin", LocalDate.of(2000, 1, 1), "admin@dionysus.com", "admin", UserRole.ADMIN);
        createDefaultUser("User", LocalDate.of(2000, 1, 1), "user@dionysus.com", "user", UserRole.USER);

        WineModelResponseDTO wineModel1 = createDefaultWineModel("Bodega Catena Zapata", "Malbec", 750, 13.5, WineType.RED, 120.00);
        WineModelResponseDTO wineModel2 = createDefaultWineModel("Château Margaux", "Pavillon Rouge", 750, 14.0, WineType.RED, 2500.00);

        createDefaultWine(wineModel1.id(), 135.00, ProductStatus.AVAILABLE);
        createDefaultWine(wineModel1.id(), 135.00, ProductStatus.UNAVAILABLE);
        createDefaultWine(wineModel2.id(), 2600.00, ProductStatus.AVAILABLE);
        createDefaultWine(wineModel2.id(), 2600.00, ProductStatus.UNAVAILABLE);
    }

    public void createDefaultUser(String name, LocalDate birthDate, String email, String password, UserRole role) {
        userService.createUser(new UserRequestDTO(name, birthDate, email, password, role));
    }

    public WineModelResponseDTO createDefaultWineModel(String brand, String name, double volume, double abv, WineType type, double recommendedPrice) {
        return wineModelService.createWineModel(new WineModelRequestDTO(brand, name, volume, abv, type, recommendedPrice));
    }

    public void createDefaultWine(Long modelId, Double price, ProductStatus status) {
        wineService.createWine(new WineRequestDTO(modelId, price, status));
    }
}
