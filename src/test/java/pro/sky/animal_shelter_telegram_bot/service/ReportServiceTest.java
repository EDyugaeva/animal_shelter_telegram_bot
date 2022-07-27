package pro.sky.animal_shelter_telegram_bot.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.animal_shelter_telegram_bot.model.PetOwner;
import pro.sky.animal_shelter_telegram_bot.model.Report;
import pro.sky.animal_shelter_telegram_bot.model.pets.Pet;
import pro.sky.animal_shelter_telegram_bot.repository.PetOwnerRepository;
import pro.sky.animal_shelter_telegram_bot.repository.PetRepository;
import pro.sky.animal_shelter_telegram_bot.repository.ReportRepository;
import pro.sky.animal_shelter_telegram_bot.service.impl.ReportServiceImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static pro.sky.animal_shelter_telegram_bot.service.ConstantsForServicesTest.*;

@ExtendWith(MockitoExtension.class)
public class ReportServiceTest {

    @Mock
    private ReportRepository reportRepositoryMock;
    @Mock
    private PetOwnerRepository petOwnerRepositoryMock;
    @Mock
    private PetRepository petRepositoryMock;

    @InjectMocks
    private ReportServiceImpl out;

    @Test
    void contextLoads() {
    }

    @Test
    public void testSetReportMark() {
        REPORT_1.setPetOwner(PET_OWNER_2);
        REPORT_1.setId(ID);

        Report expectedReport = new Report();
        expectedReport.setId(ID);
        expectedReport.setResult(RESULT);
        expectedReport.setReportChecked(true);
        expectedReport.setPetOwner(new PetOwner());

        when(reportRepositoryMock.findById(ID)).thenReturn(Optional.of(REPORT_1));

        Assertions.assertThrows(NullPointerException.class, () -> out.setMarkOnReport(ID, ""));
        Assertions.assertEquals(expectedReport, out.setMarkOnReport(ID, RESULT));
    }

    @Test
    public void testGetUncheckedReport() {
        REPORT_1.setId(ID);
        REPORT_2.setId(ID_2);

        List<Report> reportList = List.of(REPORT_1, REPORT_2);

        Assertions.assertThrows(NullPointerException.class, () -> out.getUncheckedReports());

        when(reportRepositoryMock.getUncheckedReports()).thenReturn(reportList);

        Assertions.assertEquals(reportList, out.getUncheckedReports());
    }

    @Test
    public void testFindReportByChatIdAndDate() {
        REPORT_1.setDateOfReport(DATE);

        when(reportRepositoryMock.findReportByDateOfReportAndPetOwner_ChatId(DATE, CHAT_ID)).thenReturn(Optional.of(REPORT_1));
        when(petOwnerRepositoryMock.findPetOwnerByChatId(CHAT_ID)).thenReturn(Optional.of(PET_OWNER_2));

        Report expectedReport = REPORT_1;
        expectedReport.setDateOfReport(DATE);
        expectedReport.setPetOwner(PET_OWNER_2);

        Assertions.assertEquals(expectedReport, out.findReportByChatIdAndDate(CHAT_ID, DATE));

    }


    @Test
    public void testSetReportDoDataBase() {
        Pet pet = new Pet();
        Collection<Pet> petCollection = new ArrayList<>();
        petCollection.add(pet);

        PET_OWNER_1.setChatId(CHAT_ID);

        REPORT_1.setDateOfReport(DATE);

        when(reportRepositoryMock.findReportByDateOfReportAndPetOwner_ChatId(DATE, CHAT_ID)).thenReturn(Optional.of(REPORT_1));
        when(petOwnerRepositoryMock.findPetOwnerByChatId(CHAT_ID)).thenReturn(Optional.of(PET_OWNER_1));
        when(petRepositoryMock.findPetByOwnerOfPet_Id(ID)).thenReturn(petCollection);

        String health = "Здоровье";
        String diet = "Диета";
        String behaviour = "Изменение в поведении";
        String message = "Здоровье-Диета-Изменение в поведении";

        String[] outMessage = out.setReportToDataBase(message, CHAT_ID, DATE);

        Assertions.assertEquals(health, outMessage[0]);
        Assertions.assertEquals(diet, outMessage[1]);
        Assertions.assertEquals(behaviour, outMessage[2]);

        Assertions.assertThrows(IllegalArgumentException.class, () -> out.setReportToDataBase("seufsf", CHAT_ID, DATE));

    }
}
