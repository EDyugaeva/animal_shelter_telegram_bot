package pro.sky.animal_shelter_telegram_bot.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.animal_shelter_telegram_bot.repository.PetOwnerRepository;
import pro.sky.animal_shelter_telegram_bot.repository.ReportRepository;
import pro.sky.animal_shelter_telegram_bot.service.impl.PetOwnerServiceImpl;
import pro.sky.animal_shelter_telegram_bot.service.impl.ReportServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ReportServiceTest {

    @InjectMocks
    private ReportServiceImpl out;

    @Mock
    private ReportRepository reportRepository;

    @Test
    void contextLoads() {
    }

    @Test
    private void testSetReportToDataBase() {
        String textReport = "Отчет1-отчет2-отчет3";




    }
}
