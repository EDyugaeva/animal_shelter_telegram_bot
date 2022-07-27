package pro.sky.animal_shelter_telegram_bot.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.animal_shelter_telegram_bot.model.Volunteer;
import pro.sky.animal_shelter_telegram_bot.repository.VolunteerRepository;
import pro.sky.animal_shelter_telegram_bot.service.impl.PetOwnerServiceImpl;
import pro.sky.animal_shelter_telegram_bot.service.impl.VolunteerServiceImpl;

import static org.mockito.Mockito.when;
import static pro.sky.animal_shelter_telegram_bot.service.ConstantsForServicesTest.*;

@ExtendWith(MockitoExtension.class)

public class VolunteerServiceTest {

    @InjectMocks
    private VolunteerServiceImpl out;

    @Mock
    private VolunteerRepository volunteerRepository;

    @Mock
    private PetOwnerServiceImpl petOwnerService;

    @Test
    void contextLoads() {
    }

    @Test
    public void testSetVolunteerPhoneNumber() {
        Volunteer volunteer = new Volunteer();
        Assertions.assertThrows(NullPointerException.class, () -> out.setPhoneNumberOfVolunteer(volunteer, ""));

        PET_OWNER_1.setPhoneNumber(PHONE_NUMBER);
        PET_OWNER_1.setChatId(CHAT_ID);

        when(petOwnerService.getPetOwnerChatIdByPhoneNumber(PHONE_NUMBER)).thenReturn(CHAT_ID);
        when(volunteerRepository.save(volunteer)).thenReturn(volunteer);
        Assertions.assertEquals(CHAT_ID, out.setPhoneNumberOfVolunteer(volunteer, PHONE_NUMBER).getChatId());
        Assertions.assertEquals(PHONE_NUMBER, out.setPhoneNumberOfVolunteer(volunteer, PHONE_NUMBER).getPhoneNumber());



    }


}
