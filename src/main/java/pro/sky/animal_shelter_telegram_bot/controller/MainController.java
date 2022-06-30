package pro.sky.animal_shelter_telegram_bot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping
    public String helloMessage() {
        return "Welcome to our pet shelter!";
    }
}
