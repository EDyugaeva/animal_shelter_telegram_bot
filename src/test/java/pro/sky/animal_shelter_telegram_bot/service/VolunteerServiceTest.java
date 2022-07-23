package pro.sky.animal_shelter_telegram_bot.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.animal_shelter_telegram_bot.model.PetOwner;
import pro.sky.animal_shelter_telegram_bot.model.Volunteer;
import pro.sky.animal_shelter_telegram_bot.repository.VolunteerRepository;
import pro.sky.animal_shelter_telegram_bot.service.impl.PetOwnerServiceImpl;
import pro.sky.animal_shelter_telegram_bot.service.impl.VolunteerServiceImpl;

import static org.mockito.Mockito.when;

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
        Assertions.assertThrows(NullPointerException.class, () -> out.setVolunteersPhoneNumber(volunteer, ""));

        String phoneNumber = "+7999";
        Long chatId = 123456L;

        PetOwner petOwner = new PetOwner();
        petOwner.setPhoneNumber(phoneNumber);
        petOwner.setChatId(chatId);

        when(petOwnerService.getPetOwnerChatIdByPhoneNumber(phoneNumber)).thenReturn(chatId);
        when(volunteerRepository.save(volunteer)).thenReturn(volunteer);
        Assertions.assertEquals(chatId, out.setVolunteersPhoneNumber(volunteer, phoneNumber).getChatId());
        Assertions.assertEquals(phoneNumber, out.setVolunteersPhoneNumber(volunteer, phoneNumber).getPhoneNumber());


    }


}
