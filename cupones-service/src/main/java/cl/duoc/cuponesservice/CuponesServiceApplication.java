package cl.duoc.cuponesservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Cupones Service API",
                version = "1.0",
                description = "Microservicio encargado de la gestion de cupones de descuento. Autora: Carolina Cabello."
        )
)
public class CuponesServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CuponesServiceApplication.class, args);
    }
}
