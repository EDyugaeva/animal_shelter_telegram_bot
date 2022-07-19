package pro.sky.animal_shelter_telegram_bot.controller.VolunteerControllerTests;

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
import pro.sky.animal_shelter_telegram_bot.controller.VolunteerController;
import pro.sky.animal_shelter_telegram_bot.model.Volunteer;
import pro.sky.animal_shelter_telegram_bot.repository.VolunteerRepository;
import pro.sky.animal_shelter_telegram_bot.service.impl.PetOwnerServiceImpl;
import pro.sky.animal_shelter_telegram_bot.service.impl.VolunteerServiceImpl;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pro.sky.animal_shelter_telegram_bot.controller.ConstantsForControllerTests.*;

@WebMvcTest(controllers = VolunteerController.class)
public class VolunteerControllerTestMVC {

    private final String LOCAL_URL = URL + PORT + "/" + VOLUNTEER_URL + "/";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VolunteerRepository volunteerRepository;

    @MockBean
    private PetOwnerServiceImpl petOwnerService;

    @SpyBean
    private VolunteerServiceImpl volunteerService;

    @InjectMocks
    private VolunteerController VolunteerController;

    private final Volunteer VOLUNTEER = new Volunteer();

    private final JSONObject volunteerObject = new JSONObject();

    @BeforeEach
    private void StartData() {
        volunteerObject.put("id", ID);
        volunteerObject.put("firstName", FIRST_NAME);
        volunteerObject.put("lastName", LAST_NAME);
        volunteerObject.put("chatId", CHAT_ID);
        volunteerObject.put("phoneNumber", PHONE_NUMBER);
        VOLUNTEER.setId(ID);
        VOLUNTEER.setFirstName(FIRST_NAME);
        VOLUNTEER.setLastName(LAST_NAME);
        VOLUNTEER.setChatId(CHAT_ID);
        VOLUNTEER.setPhoneNumber(PHONE_NUMBER);
    }

    @Test
    public void testFindVolunteer() throws Exception{
        when(volunteerRepository.findById(any(Long.class))).thenReturn(Optional.of(VOLUNTEER));
        mockMvc.perform(MockMvcRequestBuilders
                        .get(LOCAL_URL + ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.firstName").value(FIRST_NAME))
                .andExpect(jsonPath("$.lastName").value(LAST_NAME))
                .andExpect(jsonPath("$.chatId").value(CHAT_ID))
                .andExpect(jsonPath("$.phoneNumber").value(PHONE_NUMBER));
    }

    @Test
    public void testFindVolunteerIfNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(LOCAL_URL + ID)
                        .accept(MediaType.TEXT_PLAIN_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAddPetOwner() throws Exception {
        when(volunteerRepository.save(any(Volunteer.class))).thenReturn(VOLUNTEER);
        when(volunteerRepository.findById(any(Long.class))).thenReturn(Optional.of(VOLUNTEER));

        mockMvc.perform(MockMvcRequestBuilders
                        .post(LOCAL_URL)
                        .content(volunteerObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.firstName").value(FIRST_NAME))
                .andExpect(jsonPath("$.lastName").value(LAST_NAME))
                .andExpect(jsonPath("$.chatId").value(CHAT_ID))
                .andExpect(jsonPath("$.phoneNumber").value(PHONE_NUMBER));
    }

    @Test
    public void testEditPetOwner() throws Exception {
        when(volunteerRepository.save(any(Volunteer.class))).thenReturn(VOLUNTEER);
        when(volunteerRepository.findById(any(Long.class))).thenReturn(Optional.of(VOLUNTEER));

        mockMvc.perform(MockMvcRequestBuilders
                        .put(LOCAL_URL)
                        .content(volunteerObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.firstName").value(FIRST_NAME))
                .andExpect(jsonPath("$.lastName").value(LAST_NAME))
                .andExpect(jsonPath("$.chatId").value(CHAT_ID))
                .andExpect(jsonPath("$.phoneNumber").value(PHONE_NUMBER));
    }

    @Test
    public void testEditPetOwnerIfBadRequest() throws Exception {
        when(volunteerRepository.findById(any(Long.class))).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.put(LOCAL_URL + ID)
                        .accept(MediaType.TEXT_PLAIN_VALUE))
                .andExpect(status().is(405));
    }

    @Test
    public void testDeletePetOwner() throws Exception {
        when(volunteerRepository.save(any(Volunteer.class))).thenReturn(VOLUNTEER);
        when(volunteerRepository.findById(any(Long.class))).thenReturn(Optional.of(VOLUNTEER));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete(LOCAL_URL + ID)
                        .content(volunteerObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.firstName").value(FIRST_NAME))
                .andExpect(jsonPath("$.lastName").value(LAST_NAME))
                .andExpect(jsonPath("$.chatId").value(CHAT_ID))
                .andExpect(jsonPath("$.phoneNumber").value(PHONE_NUMBER));
    }

    @Test
    public void testDeletePetOwnerIfNotFound() throws Exception {
        when(volunteerRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        mockMvc.perform(MockMvcRequestBuilders.delete(LOCAL_URL + ID)
                        .accept(MediaType.TEXT_PLAIN_VALUE))
                .andExpect(status().isNotFound());
    }
}
