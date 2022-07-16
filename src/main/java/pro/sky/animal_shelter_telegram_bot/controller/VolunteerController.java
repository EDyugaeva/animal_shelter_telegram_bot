//package pro.sky.animal_shelter_telegram_bot.controller;
//
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.Parameter;
//import io.swagger.v3.oas.annotations.media.Content;
//import io.swagger.v3.oas.annotations.media.ExampleObject;
//import io.swagger.v3.oas.annotations.media.Schema;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import pro.sky.animal_shelter_telegram_bot.model.Volunteer;
//import pro.sky.animal_shelter_telegram_bot.service.VolunteerService;
//
//@RestController
//@RequestMapping("volunteer")
//public class VolunteerController {
//
//    private final VolunteerService volunteerService;
//
//    public VolunteerController(VolunteerService volunteerService) {
//        this.volunteerService = volunteerService;
//    }
//
//    @Operation(
//            summary = "Welcome message",
//            responses = {
//                    @ApiResponse(
//                            responseCode = "200",
//                            description = "Send a welcome message",
//                            content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE,
//                                    examples = @ExampleObject(value = "string"))
//                    )
//            },
//            tags = "Volunteers"
//    )
//    @GetMapping
//    public String helloMessage(){
//        return "You can do it by information of volunteer:\n" +
//                "1. add information about the volunteer\n" +
//                "2. get information about the volunteer\n" +
//                "2. update information about the volunteer\n" +
//                "4. remove information about rhe volunteer\n";
//    }
//
//    @Operation(
//            summary = "Find volunteer by id",
//            responses = {
//                    @ApiResponse(
//                            responseCode = "200",
//                            description = "Found volunteer:",
//                            content = @Content(
//                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
//                                    schema = @Schema(implementation = Volunteer.class))
//                    ),
//                    @ApiResponse(
//                            responseCode = "404",
//                            description = "If volunteer not found"
//                    )
//            },
//            tags = "Volunteers"
//    )
//    @GetMapping("{id}")
//    public ResponseEntity<Volunteer> findVolunteer(@Parameter(description = "Volunteer id", example = "1") @PathVariable Long id) {
//        Volunteer volunteer = volunteerService.findVolunteer(id);
//        if (volunteer == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//        return ResponseEntity.ok(volunteer);
//    }
//
//    @Operation(
//            summary = "Add information about new volunteer",
//            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
//                    description = "Add information",
//                    content = @Content(
//                            mediaType = MediaType.APPLICATION_JSON_VALUE,
//                            schema = @Schema(implementation = Volunteer.class))
//            ),
//            responses = {
//                    @ApiResponse(
//                            responseCode = "200",
//                            description = "Add information",
//                            content = @Content(
//                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
//                                    schema = @Schema(implementation = Volunteer.class))
//                    )
//            },
//            tags = "Volunteers"
//    )
//    @PostMapping
//    public Volunteer addVolunteer(@RequestBody Volunteer volunteer) {
//        return volunteerService.addVolunteer(volunteer);
//    }
//
//    @Operation(
//            summary = "Update information about a volunteer",
//            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
//                    description = "Edit information about a volunteer",
//                    content = @Content(
//                            mediaType = MediaType.APPLICATION_JSON_VALUE,
//                            schema = @Schema(implementation = Volunteer.class))
//            ),
//            responses = {
//                    @ApiResponse(
//                            responseCode = "200",
//                            description = "Update information",
//                            content = @Content(
//                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
//                                    schema = @Schema(implementation = Volunteer.class))
//                    ),
//                    @ApiResponse(
//                            responseCode = "404",
//                            description = "If volunteer not found"
//                    )
//            },
//            tags = "Volunteers"
//    )
//    @PutMapping
//    public ResponseEntity<Volunteer> editPet(@RequestBody Volunteer volunteer) {
//        Volunteer editVolunteer = volunteerService.changeVolunteer(volunteer);
//        if (editVolunteer == null) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
//        return ResponseEntity.ok(volunteer);
//    }
//
//    @Operation(
//            summary = "Delete information about volunteer",
//            responses = {
//                    @ApiResponse(
//                            responseCode = "200",
//                            description = "Volunteer is delete from Database"
//                    ),
//                    @ApiResponse(
//                            responseCode = "404",
//                            description = "If volunteer not found"
//                    )
//            },
//            tags = "Volunteers"
//    )
//    @DeleteMapping("{id}")
//    public ResponseEntity<Volunteer> deleteVolunteer(@PathVariable Long id) {
//        volunteerService.deleteVolunteer(id);
//        return ResponseEntity.ok().build();
//    }
//}
