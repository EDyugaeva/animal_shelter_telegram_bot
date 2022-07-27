package pro.sky.animal_shelter_telegram_bot.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pro.sky.animal_shelter_telegram_bot.repository.PhotoOfPetRepository;
import pro.sky.animal_shelter_telegram_bot.service.ReportService;
import pro.sky.animal_shelter_telegram_bot.service.impl.PhotoOfPetServiceImpl;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pro.sky.animal_shelter_telegram_bot.controller.ConstantsForControllerTests.*;
import static pro.sky.animal_shelter_telegram_bot.controller.ConstantsForControllerTests.REPORT_URL;

@WebMvcTest(controllers = PhotoOfPetController.class)
class PhotoOfPetControllerTest {

    private final String LOCAL_URL = URL + PORT + "/" + REPORT_URL + "/";

    @InjectMocks
    private PhotoOfPetController photoOfPetController;

    @SpyBean
    private PhotoOfPetServiceImpl photoOfPetService;

    @MockBean
    private PhotoOfPetRepository photoOfPetRepository;

    @MockBean
    private ReportService reportService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contextLoads(){
        assertThat(photoOfPetController).isNotNull();
    }

    @Test
    public void testFindPhotoByReportIdIfNotFound() throws Exception{
        when(photoOfPetRepository.findPhotoOfPetByReportId(any(Long.class))).thenReturn(Optional.empty());
        mockMvc.perform(MockMvcRequestBuilders
                        .get(LOCAL_URL + ID + "/" + PHOTO_URL)
                        .accept(MediaType.IMAGE_JPEG))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpLoadPhotoOfPetIfNonFoundReport() throws Exception {
        when(reportService.findReport(any())).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders
                        .post(LOCAL_URL)
                        .contentType(MediaType.IMAGE_JPEG)
                        .accept(MediaType.IMAGE_JPEG))
                .andExpect(status().isNotFound());
    }
}