package pro.sky.animal_shelter_telegram_bot.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import pro.sky.animal_shelter_telegram_bot.model.PetOwner;
import pro.sky.animal_shelter_telegram_bot.repository.PetOwnerRepository;
import pro.sky.animal_shelter_telegram_bot.service.impl.PetOwnerServiceImpl;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PetOwnerServiceTest {

    @InjectMocks
    private PetOwnerServiceImpl out;

    @Mock
    private PetOwnerRepository petOwnerRepository;

    @Test
    void contextLoads() {
    }


    @Test
    public void testSetPetOwnersPhoneNumber() {
        String testPhoneNumber = "+79992214290";

        Long id = 456789L;

        PetOwner petOwner = new PetOwner();

        when(petOwnerRepository.findPetOwnerByChatId(any(Long.class))).thenReturn(Optional.of(petOwner));


        Assertions.assertEquals(out.setPetOwnersPhoneNumber(testPhoneNumber, id), testPhoneNumber);

        Assertions.assertThrows(NullPointerException.class,() -> out.setPetOwnersPhoneNumber("", id));


    }


}
