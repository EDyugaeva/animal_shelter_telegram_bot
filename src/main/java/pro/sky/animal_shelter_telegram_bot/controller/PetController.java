package pro.sky.animal_shelter_telegram_bot.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.animal_shelter_telegram_bot.model.pets.Pet;

import java.util.Collection;


@RestController
@RequestMapping("/pet")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Send a welcome message",
                    content = @Content(mediaType = "string", examples = @ExampleObject(value = "example of string"))
            )
    })
    @GetMapping
    public String helloMessage(){
        return "You can do it by information of pet:\n" +
                "1. add pet information\n" +
                "2. get pet information\n" +
                "2. update pet information\n" +
                "4. remove pet information\n";
    }

    @GetMapping("{id}")
    public ResponseEntity<Pet> findStudent(@PathVariable Long id) {
        Pet pet = petService.findPet(id);
        if (pet == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(pet);
    }

    @PostMapping
    public Pet addPet(@RequestBody Pet pet) {
        return petService.addPet(pet);
    }

    @PutMapping
    public ResponseEntity<Pet> editPet(@RequestBody Pet pet) {
        Pet editPet = petService.changePet(pet);
        if (editPet == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(pet);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Pet> deletePet(@PathVariable Long id) {
        petService.deletePet(id);
        return ResponseEntity.ok().build();
    }
}
