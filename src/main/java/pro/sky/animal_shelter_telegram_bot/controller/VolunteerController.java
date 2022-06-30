package pro.sky.animal_shelter_telegram_bot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("volunteer")
public class VolunteerController {

    @GetMapping
    public String helloMessage(){
        return "You can do it by information of volunteer:\n" +
                "1. add information about the volunteer\n" +
                "2. get information about the volunteer\n" +
                "2. update information about the volunteer\n" +
                "4. remove information about rhe volunteer\n";
    }
}
