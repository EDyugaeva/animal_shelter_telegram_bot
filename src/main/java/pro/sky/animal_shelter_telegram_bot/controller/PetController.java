
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
import pro.sky.animal_shelter_telegram_bot.model.pets.Pet;
import pro.sky.animal_shelter_telegram_bot.service.PetService;

import java.util.Collection;

import static pro.sky.animal_shelter_telegram_bot.controller.ConstantsOfControllers.HELLO_MESSAGE_OF_PET_CONTROLLER;

@RestController
@RequestMapping("/pet")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
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
            tags = "Pets"
    )
    @GetMapping
    public String helloMessage(){
        return HELLO_MESSAGE_OF_PET_CONTROLLER;
    }

    @Operation(
            summary = "Find pet by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Found pet:",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Pet.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "If pets not found",
                            content = @Content(
                                    mediaType = MediaType.TEXT_PLAIN_VALUE,
                                    schema = @Schema(implementation = ResponseEntity.class)
                            )
                    )
            },
            tags = "Pets"
    )
    @GetMapping("{id}")
    public ResponseEntity<Pet> findPet(@Parameter(description = "Pet id", example = "1") @PathVariable Long id) {
        Pet pet = petService.findPet(id);
        if (pet == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(pet);
    }

    @Operation(
            summary = "Add information about new pet",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Add information",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Pet.class))
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Add information",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Pet.class))
                    )
            },
            tags = "Pets"
    )
    @PostMapping
    public Pet addPet(@RequestBody Pet pet) {
        return petService.addPet(pet);
    }

    @Operation(
            summary = "Update information about a pet",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Edit information about a pet",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Pet.class))
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Update information about pet",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Pet.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "If pets not found in Database, will be received bad request",
                            content = @Content(
                                    mediaType = MediaType.TEXT_PLAIN_VALUE,
                                    schema = @Schema(implementation = ResponseEntity.class)
                            )
                    )
            },
            tags = "Pets"
    )
    @PutMapping
    public ResponseEntity<Pet> editPet(@RequestBody Pet pet) {
        Pet editPet = petService.changePet(pet);
        if (editPet == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(pet);
    }

    @Operation(
            summary = "Delete information about pet",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Pet is delete from Database",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Pet.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "if Pet don't delete, because pet not found in Database",
                            content = @Content(
                                    mediaType = MediaType.TEXT_PLAIN_VALUE,
                                    schema = @Schema(implementation = ResponseEntity.class)
                            )
                    )
            },
            tags = "Pets"
    )
    @DeleteMapping("{id}")
    public ResponseEntity<Pet> deletePet(@PathVariable Long id) {
        if (petService.deletePet(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(petService.deletePet(id));
    }

    @Operation(
            summary = "Find all pets in pet shelter",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Finding pets",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Collection.class))
                    )
            },
            tags = "Pets"
    )
    @GetMapping("/all")
    public ResponseEntity<Collection<Pet>> findAllPets() {
        return ResponseEntity.ok(petService.getAllPets());
    }
}
