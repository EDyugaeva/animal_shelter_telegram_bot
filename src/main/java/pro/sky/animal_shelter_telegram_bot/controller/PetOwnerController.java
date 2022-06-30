package pro.sky.animal_shelter_telegram_bot.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pet-owner")
public class PetOwnerController {

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Send a welcome message",
                    content = @Content(mediaType = "string", examples = @ExampleObject(value = "example of string"))
            )
    })
    @GetMapping
    public String helloMessage(){
        return "You can do it by information of pet owner:\n" +
                "1. add information about the owner of the pet\n" +
                "2. get information about the owner of the pet\n" +
                "2. update information about the owner of the pet\n" +
                "4. remove information about the owner of the pet\n";
    }
}
