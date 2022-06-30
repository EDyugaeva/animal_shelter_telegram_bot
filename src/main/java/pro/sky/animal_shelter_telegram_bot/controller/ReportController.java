package pro.sky.animal_shelter_telegram_bot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report")
public class ReportController {

    @GetMapping
    public String helloMessage(){
        return "You can do it by reports\n" +
                "1. add report\n" +
                "2. get report\n" +
                "2. update report\n" +
                "4. remove report\n";
    }
}
