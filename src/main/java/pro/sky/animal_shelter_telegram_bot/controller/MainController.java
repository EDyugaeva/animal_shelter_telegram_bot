//package pro.sky.animal_shelter_telegram_bot.controller;
//
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.media.Content;
//import io.swagger.v3.oas.annotations.media.ExampleObject;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class MainController {
//
//    @Operation(
//            summary = "Welcome message in our pet shelter",
//            responses = {
//                    @ApiResponse(
//                            responseCode = "200",
//                            description = "Send a welcome message",
//                            content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE,
//                                    examples = @ExampleObject(value = "string"))
//                    )
//            },
//            tags = "Pet shelter (home page)"
//    )
//    @GetMapping
//    public String helloMessage() {
//        return "Welcome to our pet shelter!";
//    }
//}
