package pro.sky.animal_shelter_telegram_bot;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class AnimalShelterTelegramBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnimalShelterTelegramBotApplication.class, args);
    }

}
