
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

import java.util.Collection;

import static pro.sky.animal_shelter_telegram_bot.controller.ConstantsOfControllers.HELLO_MESSAGE_OF_PET_OWNER_CONTROLLER;

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
    public String helloMessage() {
        return HELLO_MESSAGE_OF_PET_OWNER_CONTROLLER;
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
                            description = "If pet owner not found",
                            content = @Content(
                                    mediaType = MediaType.TEXT_PLAIN_VALUE,
                                    schema = @Schema(implementation = ResponseEntity.class)
                            )
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
                            responseCode = "400",
                            description = "If pet owner not found, will be received bad request",
                            content = @Content(
                                    mediaType = MediaType.TEXT_PLAIN_VALUE,
                                    schema = @Schema(implementation = ResponseEntity.class))
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
                            description = "Pet owner is delete from Database",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = PetOwner.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "If pets not found",
                            content = @Content(
                                    mediaType = MediaType.TEXT_PLAIN_VALUE,
                                    schema = @Schema(implementation = ResponseEntity.class))
                    )
            },
            tags = "Pet owners"
    )
    @DeleteMapping("{id}")
    public ResponseEntity<PetOwner> deletePetOwner(@PathVariable Long id) {
        if (petOwnerService.deletePetOwner(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(petOwnerService.deletePetOwner(id));
    }


    @Operation(
            summary = "Find pet owners with day of probation = 0",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Found pet owners:",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = PetOwner.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "If pet owner not found",
                            content = @Content(
                                    mediaType = MediaType.TEXT_PLAIN_VALUE,
                                    schema = @Schema(implementation = ResponseEntity.class)
                            )
                    )
            },
            tags = "Pet owners"
    )
    @GetMapping(path = "/zero-probation")
    public ResponseEntity<Collection<PetOwner>> findPetOwner() {
        Collection<PetOwner> petOwner = petOwnerService.getPetOwnerWithZeroDayOfProbation();
        return ResponseEntity.ok(petOwner);
    }

    @Operation(
            summary = "Update information about a pet owner",
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
                            description = "If pet owner not found",
                            content = @Content(
                                    mediaType = MediaType.TEXT_PLAIN_VALUE,
                                    schema = @Schema(implementation = ResponseEntity.class)
                            )
                    )

            },
            tags = "Pet owners"
    )
    @PutMapping("/probation-days")
    public ResponseEntity<PetOwner> changeDayOfProbationInPetOwner(@Parameter(description = "Pet owner id", example = "15") @RequestParam Long id,
                                                                   @Parameter(description = "amount of extra day (could be negative)", example = "-2") @RequestParam Integer amountOfDays) {
        PetOwner petOwner = petOwnerService.setExtraDayOfProbation(id, amountOfDays);

        return ResponseEntity.ok(petOwner);
    }

    @Operation (
            summary = "Say, that probation is over",
            responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Update information",
                    content = @Content(
                            mediaType = MediaType.TEXT_PLAIN_VALUE)
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "If pet owner not found",
                    content = @Content(
                            mediaType = MediaType.TEXT_PLAIN_VALUE
                    )
            )
    },
            tags = "Pet owners"
    )
    @PutMapping("/probation")
    public ResponseEntity<String> probationIsOver(@Parameter (description = "Pet owner id", example = "25")@RequestParam Long id) {
        String message = petOwnerService.sayThatProbationIsOverSuccessfully(id);
        return ResponseEntity.ok(message);
    }

    @Operation (
            summary = "Say, that probation is over",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Update information",
                            content = @Content(
                                    mediaType = MediaType.TEXT_PLAIN_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "If pet owner not found",
                            content = @Content(
                                    mediaType = MediaType.TEXT_PLAIN_VALUE
                            )
                    )

            },
            tags = "Pet owners"
    )
    @PutMapping("/probation-unsuccessfully")
    public ResponseEntity<String> probationIsOverUnsuccessfully(@Parameter (description = "Pet owner id", example = "25")@RequestParam Long id) {
        String message = petOwnerService.sayThatProbationIsOverNotSuccessfully(id);
        return ResponseEntity.ok(message);
    }

    @Operation(
            summary = "Find all pet owners in pet shelter",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Finding pet owners",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Collection.class))
                    )
            },
            tags = "Pet owners"
    )
    @GetMapping("/all")
    public ResponseEntity<Collection<PetOwner>> findAllPetOwners() {
        return ResponseEntity.ok(petOwnerService.getAllPetOwners());
    }
}
