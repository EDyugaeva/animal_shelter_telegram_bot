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
import pro.sky.animal_shelter_telegram_bot.model.Report;
import pro.sky.animal_shelter_telegram_bot.repository.PetOwnerRepository;
import pro.sky.animal_shelter_telegram_bot.repository.ReportRepository;
import pro.sky.animal_shelter_telegram_bot.service.impl.ReportServiceImpl;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pro.sky.animal_shelter_telegram_bot.controller.ConstantsForControllerTests.*;
import static pro.sky.animal_shelter_telegram_bot.controller.ConstantsOfControllers.HELLO_MESSAGE_OF_REPORT_CONTROLLER;

@WebMvcTest(controllers = ReportController.class)
public class ReportControllerTest {
    private final String LOCAL_URL = URL + PORT + "/" + REPORT_URL + "/";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportRepository reportRepository;

    @MockBean
    private PetOwnerRepository petOwnerRepository;

    @SpyBean
    private ReportServiceImpl reportService;

    @InjectMocks
    private ReportController reportController;

    private final Report REPORT = new Report();

    private final JSONObject reportObject = new JSONObject();

    @BeforeEach
    private void StartData() {
        reportObject.put("id", ID);
        reportObject.put("dateOfReport", DATE_OF_REPORT);
        reportObject.put("diet", DIET);
        reportObject.put("health", HEALTH);
        reportObject.put("result", RESULT);
        REPORT.setId(ID);
        REPORT.setdateOfReport(DATE_OF_REPORT);
        REPORT.setDiet(DIET);
        REPORT.setHealth(HEALTH);
        REPORT.setResult(RESULT);
    }

    @Test
    public void contextLoads(){
        assertThat(reportController).isNotNull();
    }

    @Test
    public void testHelloMessage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(LOCAL_URL)
                        .accept(MediaType.TEXT_PLAIN_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().string(HELLO_MESSAGE_OF_REPORT_CONTROLLER));
    }

    @Test
    public void testFindReport() throws Exception{
        when(reportRepository.findById(any(Long.class))).thenReturn(Optional.of(REPORT));
        mockMvc.perform(MockMvcRequestBuilders
                        .get(LOCAL_URL + ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.dateOfReport").value(DATE_OF_REPORT))
                .andExpect(jsonPath("$.diet").value(DIET))
                .andExpect(jsonPath("$.health").value(HEALTH))
                .andExpect(jsonPath("$.result").value(RESULT));
    }

    @Test
    public void testFindReportIfNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(LOCAL_URL + ID)
                        .accept(MediaType.TEXT_PLAIN_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAddPetOwner() throws Exception {
        when(reportRepository.save(any(Report.class))).thenReturn(REPORT);
        when(reportRepository.findById(any(Long.class))).thenReturn(Optional.of(REPORT));

        mockMvc.perform(MockMvcRequestBuilders
                        .post(LOCAL_URL)
                        .content(reportObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.dateOfReport").value(DATE_OF_REPORT))
                .andExpect(jsonPath("$.diet").value(DIET))
                .andExpect(jsonPath("$.health").value(HEALTH))
                .andExpect(jsonPath("$.result").value(RESULT));
    }

    @Test
    public void testEditReport() throws Exception {
        when(reportRepository.save(any(Report.class))).thenReturn(REPORT);
        when(reportRepository.findById(any(Long.class))).thenReturn(Optional.of(REPORT));

        mockMvc.perform(MockMvcRequestBuilders
                        .put(LOCAL_URL)
                        .content(reportObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.dateOfReport").value(DATE_OF_REPORT))
                .andExpect(jsonPath("$.diet").value(DIET))
                .andExpect(jsonPath("$.health").value(HEALTH))
                .andExpect(jsonPath("$.result").value(RESULT));
    }

    @Test
    public void testEditReportIfBadRequest() throws Exception {
        when(reportRepository.findById(any(Long.class))).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.put(LOCAL_URL + ID)
                        .accept(MediaType.TEXT_PLAIN_VALUE))
                .andExpect(status().is(405));
    }

    @Test
    public void testDeleteReport() throws Exception {
        when(reportRepository.save(any(Report.class))).thenReturn(REPORT);
        when(reportRepository.findById(any(Long.class))).thenReturn(Optional.of(REPORT));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete(LOCAL_URL + ID)
                        .content(reportObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.dateOfReport").value(DATE_OF_REPORT))
                .andExpect(jsonPath("$.diet").value(DIET))
                .andExpect(jsonPath("$.health").value(HEALTH))
                .andExpect(jsonPath("$.result").value(RESULT));
    }

    @Test
    public void testDeleteReportIfNotFound() throws Exception {
        when(reportRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        mockMvc.perform(MockMvcRequestBuilders.delete(LOCAL_URL + ID)
                        .accept(MediaType.TEXT_PLAIN_VALUE))
                .andExpect(status().isNotFound());
    }
}
