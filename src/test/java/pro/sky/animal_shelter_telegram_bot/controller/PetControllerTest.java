package pro.sky.animal_shelter_telegram_bot.controller;

import net.minidev.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pro.sky.animal_shelter_telegram_bot.model.pets.Pet;
import pro.sky.animal_shelter_telegram_bot.repository.PetRepository;
import pro.sky.animal_shelter_telegram_bot.service.impl.PetServiceImpl;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pro.sky.animal_shelter_telegram_bot.controller.ConstantsForControllerTests.*;
import static pro.sky.animal_shelter_telegram_bot.controller.ConstantsOfControllers.HELLO_MESSAGE_OF_PET_CONTROLLER;

@WebMvcTest(controllers = PetController.class)
public class PetControllerTest {

    private final String LOCAL_URL = URL + PORT + "/" + PET_URL + "/";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PetRepository petRepository;

    @SpyBean
    private PetServiceImpl petService;

    @InjectMocks
    private PetController petController;

    private final JSONObject petOwnerObject = new JSONObject();

    @BeforeEach
    private void StartData() {
        petOwnerObject.put("id", ID);
        petOwnerObject.put("nameOfPet", NAME_OF_PET);
        petOwnerObject.put("health", HEALTH);
        petOwnerObject.put("extraInfoOfPet", EXTRA_INFO);
        petOwnerObject.put("ownerOfPet", PET_OWNER);
        PET.setId(ID);
        PET.setNameOfPet(NAME_OF_PET);
        PET.setHealth(HEALTH);
        PET.setExtraInfoOfPet(EXTRA_INFO);
        PET.setOwnerOfPet(PET_OWNER);
    }

    @Test
    public void contextLoads(){
        assertThat(petController).isNotNull();
    }

    @Test
    public void testHelloMessage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(LOCAL_URL)
                        .accept(MediaType.TEXT_PLAIN_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().string(HELLO_MESSAGE_OF_PET_CONTROLLER));
    }

    @Test
    public void testFindPet() throws Exception{
        when(petRepository.findById(any(Long.class))).thenReturn(Optional.of(PET));
        mockMvc.perform(MockMvcRequestBuilders
                        .get(LOCAL_URL + ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.nameOfPet").value(NAME_OF_PET))
                .andExpect(jsonPath("$.health").value(HEALTH))
                .andExpect(jsonPath("$.extraInfoOfPet").value(EXTRA_INFO))
                .andExpect(jsonPath("$.ownerOfPet").value(PET_OWNER));
    }

    @Test
    public void testFindPetIfNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(LOCAL_URL + ID)
                        .accept(MediaType.TEXT_PLAIN_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAddPetOwner() throws Exception {
        when(petRepository.save(any(Pet.class))).thenReturn(PET);
        when(petRepository.findById(any(Long.class))).thenReturn(Optional.of(PET));

        mockMvc.perform(MockMvcRequestBuilders
                        .post(LOCAL_URL)
                        .content(petOwnerObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.nameOfPet").value(NAME_OF_PET))
                .andExpect(jsonPath("$.health").value(HEALTH))
                .andExpect(jsonPath("$.extraInfoOfPet").value(EXTRA_INFO))
                .andExpect(jsonPath("$.ownerOfPet").value(PET_OWNER));
    }

    @Test
    public void testEditPet() throws Exception {
        when(petRepository.save(any(Pet.class))).thenReturn(PET);
        when(petRepository.findById(any(Long.class))).thenReturn(Optional.of(PET));

        mockMvc.perform(MockMvcRequestBuilders
                        .put(LOCAL_URL)
                        .content(petOwnerObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.nameOfPet").value(NAME_OF_PET))
                .andExpect(jsonPath("$.health").value(HEALTH))
                .andExpect(jsonPath("$.extraInfoOfPet").value(EXTRA_INFO))
                .andExpect(jsonPath("$.ownerOfPet").value(PET_OWNER));
    }

    @Test
    public void testEditPetIfBadRequest() throws Exception {
        when(petRepository.findById(any(Long.class))).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.put(LOCAL_URL + ID)
                        .accept(MediaType.TEXT_PLAIN_VALUE))
                .andExpect(status().is(405));
    }

    @Test
    public void testDeletePet() throws Exception {
        when(petRepository.save(any(Pet.class))).thenReturn(PET);
        when(petRepository.findById(any(Long.class))).thenReturn(Optional.of(PET));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete(LOCAL_URL + ID)
                        .content(petOwnerObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.nameOfPet").value(NAME_OF_PET))
                .andExpect(jsonPath("$.health").value(HEALTH))
                .andExpect(jsonPath("$.extraInfoOfPet").value(EXTRA_INFO))
                .andExpect(jsonPath("$.ownerOfPet").value(PET_OWNER));
    }

    @Test
    public void testDeletePetIfNotFound() throws Exception {
        when(petRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        mockMvc.perform(MockMvcRequestBuilders.delete(LOCAL_URL + ID)
                        .accept(MediaType.TEXT_PLAIN_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testFindAllPets() throws Exception{
        when(petRepository.findAll()).thenReturn(new ArrayList<>(List.of(PET)));
        mockMvc.perform(MockMvcRequestBuilders
                        .get(LOCAL_URL + ALL_URL)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
