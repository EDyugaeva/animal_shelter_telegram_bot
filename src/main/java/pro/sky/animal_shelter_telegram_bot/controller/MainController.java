package pro.sky.animal_shelter_telegram_bot.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Send a welcome message",
                    content = @Content(mediaType = "string", examples = @ExampleObject(value = "example of string"))
            )
    })
    @GetMapping
    public String helloMessage() {
        return "Welcome to our pet shelter!";
    }
}
