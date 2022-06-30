package pro.sky.animal_shelter_telegram_bot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/pet")
public class PetController {

    @GetMapping
    public String helloMessage(){
        return "You can do it by information of pet:\n" +
                "1. add pet information\n" +
                "2. get pet information\n" +
                "2. update pet information\n" +
                "4. remove pet information\n";
    }
}
