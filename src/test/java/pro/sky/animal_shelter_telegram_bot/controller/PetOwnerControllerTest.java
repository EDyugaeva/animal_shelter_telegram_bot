package pro.sky.animal_shelter_telegram_bot.controller;

import com.pengrad.telegrambot.TelegramBot;
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
import pro.sky.animal_shelter_telegram_bot.model.PetOwner;
import pro.sky.animal_shelter_telegram_bot.repository.PetOwnerRepository;
import pro.sky.animal_shelter_telegram_bot.service.impl.PetOwnerServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pro.sky.animal_shelter_telegram_bot.controller.ConstantsForControllerTests.*;
import static pro.sky.animal_shelter_telegram_bot.controller.ConstantsOfControllers.HELLO_MESSAGE_OF_PET_OWNER_CONTROLLER;

@WebMvcTest(controllers = PetOwnerController.class)
public class PetOwnerControllerTest {

    private final String LOCAL_URL = URL + PORT + "/" + PET_OWNER_URL + "/";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PetOwnerRepository petOwnerRepository;

    @MockBean
    private TelegramBot telegramBot;

    @SpyBean
    private PetOwnerServiceImpl petOwnerService;

    @InjectMocks
    private PetOwnerController petOwnerController;

    private final PetOwner PET_OWNER = new PetOwner();
    private final JSONObject petOwnerObject = new JSONObject();

    @BeforeEach
    private void StartData() {
        petOwnerObject.put("id", ID);
        petOwnerObject.put("firstName", FIRST_NAME);
        petOwnerObject.put("lastName", LAST_NAME);
        petOwnerObject.put("chatId", CHAT_ID);
        petOwnerObject.put("phoneNumber", PHONE_NUMBER);
        petOwnerObject.put("dayOfProbation", DAY_OF_PROBATION);
        PET_OWNER.setId(ID);
        PET_OWNER.setFirstName(FIRST_NAME);
        PET_OWNER.setLastName(LAST_NAME);
        PET_OWNER.setChatId(CHAT_ID);
        PET_OWNER.setPhoneNumber(PHONE_NUMBER);
        PET_OWNER.setDayOfProbation(DAY_OF_PROBATION);
    }

    @Test
    public void contextLoads(){
        assertThat(petOwnerController).isNotNull();
    }

    @Test
    public void testHelloMessage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(LOCAL_URL)
                        .accept(MediaType.TEXT_PLAIN_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().string(HELLO_MESSAGE_OF_PET_OWNER_CONTROLLER));
    }

    @Test
    public void testFindPetOwner() throws Exception{
            when(petOwnerRepository.findById(any(Long.class))).thenReturn(Optional.of(PET_OWNER));
            mockMvc.perform(MockMvcRequestBuilders
                            .get(LOCAL_URL + ID)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(ID))
                    .andExpect(jsonPath("$.firstName").value(FIRST_NAME))
                    .andExpect(jsonPath("$.lastName").value(LAST_NAME))
                    .andExpect(jsonPath("$.chatId").value(CHAT_ID))
                    .andExpect(jsonPath("$.phoneNumber").value(PHONE_NUMBER))
                    .andExpect(jsonPath("$.dayOfProbation").value(DAY_OF_PROBATION));
    }

    @Test
    public void testFindPetOwnerIfNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(LOCAL_URL + ID)
                        .accept(MediaType.TEXT_PLAIN_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAddPetOwner() throws Exception {
        when(petOwnerRepository.save(any(PetOwner.class))).thenReturn(PET_OWNER);
        when(petOwnerRepository.findById(any(Long.class))).thenReturn(Optional.of(PET_OWNER));

        mockMvc.perform(MockMvcRequestBuilders
                        .post(LOCAL_URL)
                        .content(petOwnerObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.firstName").value(FIRST_NAME))
                .andExpect(jsonPath("$.lastName").value(LAST_NAME))
                .andExpect(jsonPath("$.chatId").value(CHAT_ID))
                .andExpect(jsonPath("$.phoneNumber").value(PHONE_NUMBER))
                .andExpect(jsonPath("$.dayOfProbation").value(DAY_OF_PROBATION));
    }

    @Test
    public void testEditPetOwner() throws Exception {
        when(petOwnerRepository.save(any(PetOwner.class))).thenReturn(PET_OWNER);
        when(petOwnerRepository.findById(any(Long.class))).thenReturn(Optional.of(PET_OWNER));

        mockMvc.perform(MockMvcRequestBuilders
                        .put(LOCAL_URL)
                        .content(petOwnerObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.firstName").value(FIRST_NAME))
                .andExpect(jsonPath("$.lastName").value(LAST_NAME))
                .andExpect(jsonPath("$.chatId").value(CHAT_ID))
                .andExpect(jsonPath("$.phoneNumber").value(PHONE_NUMBER))
                .andExpect(jsonPath("$.dayOfProbation").value(DAY_OF_PROBATION));
    }

    @Test
    public void testEditPetOwnerIfBadRequest() throws Exception {
        when(petOwnerRepository.findById(any(Long.class))).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.put(LOCAL_URL + ID)
                        .accept(MediaType.TEXT_PLAIN_VALUE))
                .andExpect(status().is(405));
    }

    @Test
    public void testDeletePetOwner() throws Exception {
        when(petOwnerRepository.save(any(PetOwner.class))).thenReturn(PET_OWNER);
        when(petOwnerRepository.findById(any(Long.class))).thenReturn(Optional.of(PET_OWNER));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete(LOCAL_URL + ID)
                        .content(petOwnerObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.firstName").value(FIRST_NAME))
                .andExpect(jsonPath("$.lastName").value(LAST_NAME))
                .andExpect(jsonPath("$.chatId").value(CHAT_ID))
                .andExpect(jsonPath("$.phoneNumber").value(PHONE_NUMBER))
                .andExpect(jsonPath("$.dayOfProbation").value(DAY_OF_PROBATION));
    }

    @Test
    public void testDeletePetOwnerIfNotFound() throws Exception {
        when(petOwnerRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        mockMvc.perform(MockMvcRequestBuilders.delete(LOCAL_URL + ID)
                        .accept(MediaType.TEXT_PLAIN_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testFindPetOwners() throws Exception{
        when(petOwnerRepository.findAll()).thenReturn(new ArrayList<>(List.of(PET_OWNER)));
        mockMvc.perform(MockMvcRequestBuilders
                        .get(LOCAL_URL + ALL_URL)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
