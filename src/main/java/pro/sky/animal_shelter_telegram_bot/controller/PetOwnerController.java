package pro.sky.animal_shelter_telegram_bot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.animal_shelter_telegram_bot.model.PetOwner;
import pro.sky.animal_shelter_telegram_bot.service.PetOwnerService;

@RestController
@RequestMapping("/pet-owner")
public class PetOwnerController {

    private final PetOwnerService petOwnerService;

    public PetOwnerController(PetOwnerService petOwnerService) {
        this.petOwnerService = petOwnerService;
    }

    @Operation(
            summary = "Welcome message",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Send a welcome message",
                            content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE,
                                    examples = @ExampleObject(value = "string"))
                    )
            },
            tags = "Pet owners"
    )
    @GetMapping
    public String helloMessage(){
        return "You can do it by information of pet owner:\n" +
                "1. add information about the owner of the pet\n" +
                "2. get information about the owner of the pet\n" +
                "2. update information about the owner of the pet\n" +
                "4. remove information about the owner of the pet\n";
    }

    @Operation(
            summary = "Find pet owner by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Found pet owner:",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = PetOwner.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "If pet owner not found"
                    )
            },
            tags = "Pet owners"
    )
    @GetMapping("{id}")
    public ResponseEntity<PetOwner> findPetOwner(@Parameter(description = "Pet owner id", example = "1") @PathVariable Long id) {
        PetOwner petOwner = petOwnerService.findPetOwner(id);
        if (petOwner == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(petOwner);
    }

    @Operation(
            summary = "Add information about new pet owner",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Add information",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PetOwner.class))
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Add information",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = PetOwner.class))
                    )
            },
            tags = "Pet owners"
    )
    @PostMapping
    public PetOwner addPetOwner(@RequestBody PetOwner petOwner) {
        return petOwnerService.addPetOwner(petOwner);
    }

    @Operation(
            summary = "Update information about a pet owner",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Edit information about a pet owner",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PetOwner.class))
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Update information",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = PetOwner.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "If pet owner not found"
                    )
            },
            tags = "Pet owners"
    )
    @PutMapping
    public ResponseEntity<PetOwner> editPetOwner(@RequestBody PetOwner petOwner) {
        PetOwner editPetOwner = petOwnerService.changePetOwner(petOwner);
        if (editPetOwner == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(petOwner);
    }

    @Operation(
            summary = "Delete information about pet owner",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Pet owner is delete from Database"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "If pets not found"
                    )
            },
            tags = "Pet owners"
    )
    @DeleteMapping("{id}")
    public ResponseEntity<PetOwner> deletePetOwner(@PathVariable Long id) {
        petOwnerService.deletePetOwner(id);
        return ResponseEntity.ok().build();
    }
}
