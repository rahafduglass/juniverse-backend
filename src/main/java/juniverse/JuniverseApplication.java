package juniverse;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "juniverse")
@OpenAPIDefinition
public class JuniverseApplication {

    public static void main(String[] args) {
        SpringApplication.run(JuniverseApplication.class, args);
    }


}
