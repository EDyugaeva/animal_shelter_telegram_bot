package pro.sky.animal_shelter_telegram_bot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pet-owner")
public class PetOwnerController {

    @GetMapping
    public String helloMessage(){
        return "You can do it by information of pet owner:\n" +
                "1. add information about the owner of the pet\n" +
                "2. get information about the owner of the pet\n" +
                "2. update information about the owner of the pet\n" +
                "4. remove information about the owner of the pet\n";
    }
}
