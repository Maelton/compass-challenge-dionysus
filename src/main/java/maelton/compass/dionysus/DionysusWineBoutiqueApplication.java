package maelton.compass.dionysus;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(title = "Dionysus Wine Boutique",
		version = "1.0.0",
		description = "Backend application for an ecommerce wine store."
	)
)
//@SecurityScheme(name = "jwtAuthentication", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
@SpringBootApplication
public class DionysusWineBoutiqueApplication {
	public static void main(String[] args) {
		SpringApplication.run(DionysusWineBoutiqueApplication.class, args);
	}
}
