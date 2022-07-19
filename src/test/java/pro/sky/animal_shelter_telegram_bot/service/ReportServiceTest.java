package pro.sky.animal_shelter_telegram_bot.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.animal_shelter_telegram_bot.model.PetOwner;
import pro.sky.animal_shelter_telegram_bot.model.Report;
import pro.sky.animal_shelter_telegram_bot.repository.PetOwnerRepository;
import pro.sky.animal_shelter_telegram_bot.repository.ReportRepository;
import pro.sky.animal_shelter_telegram_bot.service.impl.ReportServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReportServiceTest {

    @Mock
    private ReportRepository reportRepositoryMock;
    @Mock
    private PetOwnerRepository petOwnerRepositoryMock;

    @InjectMocks
    private ReportServiceImpl out;

    @Test
    void contextLoads() {
    }

    @Test
    public void testSetReportMark() {
        String result = "Хороший";
        Report report = new Report();
        report.setPetOwner(new PetOwner());
        report.setId(1L);

        Report expectedReport = new Report();
        expectedReport.setId(1L);
        expectedReport.setResult(result);
        expectedReport.setReportChecked(true);
        expectedReport.setPetOwner(new PetOwner());

        when(reportRepositoryMock.findById(1L)).thenReturn(Optional.of(report));

        Assertions.assertThrows(NullPointerException.class,() -> out.setMarkOnReport(1L, ""));
        Assertions.assertEquals(expectedReport,out.setMarkOnReport(1L, result));
    }


    @Test
    public void testGetUncheckedReport() {
        Report report1 = new Report();
        Report report2 = new Report();

        report1.setId(1L);
        report2.setId(2L);

        List<Report> reportList = List.of(report1, report2);

        Assertions.assertThrows(NullPointerException.class, () -> out.getUncheckedReports());

        when(reportRepositoryMock.getUncheckedReports()).thenReturn(reportList);

        Assertions.assertEquals(reportList, out.getUncheckedReports());


    }

    @Test
    public void testFindReportByChatIdAndDate() {
        Long chatId = 456789L;
        String date = "17.07.22";
        PetOwner petOwner = new PetOwner();
        petOwner.setChatId(chatId);

        Report report = new Report();
        report.setdateOfReport(date);

        when(reportRepositoryMock.findReportByDateOfReportAndPetOwner_ChatId(date,chatId)).thenReturn(Optional.of(report));
        when(petOwnerRepositoryMock.findPetOwnerByChatId(chatId)).thenReturn(Optional.of(petOwner));

        Report expectedReport = report;
        expectedReport.setdateOfReport(date);
        expectedReport.setPetOwner(petOwner);

        Assertions.assertEquals(expectedReport, out.findReportByChatIdAndDate(chatId, date));

    }


    @Test
    public void testSetReportDoDataBase() {
        Long chatId = 456789L;
        String date = "17.07.22";

        PetOwner petOwner = new PetOwner();
        petOwner.setChatId(chatId);

        Report report = new Report();
        report.setdateOfReport(date);

        when(reportRepositoryMock.findReportByDateOfReportAndPetOwner_ChatId(date,chatId)).thenReturn(Optional.of(report));
        when(petOwnerRepositoryMock.findPetOwnerByChatId(chatId)).thenReturn(Optional.of(petOwner));

        String health = "Здоровье";
        String  diet = "Диета";
        String  behaviour = "Изменение в поведении";
        String message = "Здоровье-Диета-Изменение в поведении";

        String[] outMessage = out.setReportToDataBase(message,chatId, date) ;

        Assertions.assertEquals(health, outMessage[0]);
        Assertions.assertEquals(diet, outMessage[1]);
        Assertions.assertEquals(behaviour, outMessage[2]);

        Assertions.assertThrows(IllegalArgumentException.class, () -> out.setReportToDataBase("seufsf", chatId, date));

    }
}
