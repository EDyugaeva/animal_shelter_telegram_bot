package pro.sky.animal_shelter_telegram_bot.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.webjars.NotFoundException;
import pro.sky.animal_shelter_telegram_bot.model.PetOwner;
import pro.sky.animal_shelter_telegram_bot.repository.PetOwnerRepository;
import pro.sky.animal_shelter_telegram_bot.service.impl.PetOwnerServiceImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static pro.sky.animal_shelter_telegram_bot.service.ConstantsForServicesTest.*;

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
        when(petOwnerRepository.findPetOwnerByChatId(any(Long.class))).thenReturn(Optional.of(PET_OWNER_1));

        Assertions.assertEquals(out.setPetOwnersPhoneNumber(PHONE_NUMBER, ID), PHONE_NUMBER);
        Assertions.assertThrows(NullPointerException.class, () -> out.setPetOwnersPhoneNumber("", ID));

    }

    @Test
    public void testGetPetOwnerByDayOfProbation() {
        when(petOwnerRepository.getPetOwnerByDayOfProbation()).thenReturn(new ArrayList<>());

        Assertions.assertThrows(NotFoundException.class, () -> out.getPetOwnerByDayOfProbation());

        PET_OWNER_1.setDayOfProbation(5);
        PET_OWNER_2.setDayOfProbation(2);

        COLLECTION_PET_OWNERS_NOT_ZERO.add(PET_OWNER_1);
        COLLECTION_PET_OWNERS_NOT_ZERO.add(PET_OWNER_2);

        Collection<PetOwner> expectedCollectionPetOwner = new ArrayList<>();
        expectedCollectionPetOwner.add(PET_OWNER_1);
        expectedCollectionPetOwner.add(PET_OWNER_1);
        when(petOwnerRepository.getPetOwnerByDayOfProbation()).thenReturn(COLLECTION_PET_OWNERS_NOT_ZERO);

        Assertions.assertEquals(expectedCollectionPetOwner, out.getPetOwnerByDayOfProbation());
    }

    @Test
    public void testGetPetOwnerByZeroDayOfProbation() {
        when(petOwnerRepository.getPetOwnerWithZeroDayOfProbation()).thenReturn(new ArrayList<>());

        Assertions.assertThrows(NotFoundException.class, () -> out.getPetOwnerWithZeroDayOfProbation());

        PET_OWNER_1.setDayOfProbation(0);
        PET_OWNER_2.setDayOfProbation(0);


        COLLECTION_PET_OWNERS_ZERO.add(PET_OWNER_1);
        COLLECTION_PET_OWNERS_ZERO.add(PET_OWNER_2);

        Collection<PetOwner> expectedCollectionPetOwner = new ArrayList<>();
        expectedCollectionPetOwner.add(PET_OWNER_1);
        expectedCollectionPetOwner.add(PET_OWNER_2);
        when(petOwnerRepository.getPetOwnerWithZeroDayOfProbation()).thenReturn(COLLECTION_PET_OWNERS_ZERO);

        Assertions.assertEquals(expectedCollectionPetOwner, out.getPetOwnerWithZeroDayOfProbation());
    }

    @Test
    public void testSetOwnersName() {

        when(petOwnerRepository.findPetOwnerByChatId(CHAT_ID)).thenReturn(Optional.of(PET_OWNER_1));

        Assertions.assertThrows(NullPointerException.class, () -> out.setPetOwnersName("", CHAT_ID));

        PetOwner expectedPetOwner = PET_OWNER_1;
        expectedPetOwner.setFirstName(NAME);

        Assertions.assertEquals(expectedPetOwner, out.setPetOwnersName(NAME, CHAT_ID));

    }

    @Test
    public void testGetPetOwnerChatIdByPhoneNumber() {
        PET_OWNER_1.setPhoneNumber(PHONE_NUMBER);
        PET_OWNER_1.setChatId(CHAT_ID);

        when(petOwnerRepository.findPetOwnerByPhoneNumber(PHONE_NUMBER)).thenReturn(Optional.of(PET_OWNER_1));

        Assertions.assertEquals(CHAT_ID, out.getPetOwnerChatIdByPhoneNumber(PHONE_NUMBER));
    }

    @Test
    public void testSetExtraDayOfProbation() {
        PET_OWNER_1.setDayOfProbation(25);

        when(petOwnerRepository.findById(ID)).thenReturn(Optional.of(PET_OWNER_1));

        Assertions.assertThrows(NotFoundException.class, () -> out.setExtraDayOfProbation(ID, 5));

        PET_OWNER_1.setId(ID);
        PetOwner expectedPetOwner = PET_OWNER_1;
        expectedPetOwner.setDayOfProbation(25 + 5);

        Assertions.assertEquals(expectedPetOwner, out.setExtraDayOfProbation(ID, 5));

        expectedPetOwner.setDayOfProbation(25 + 0);

        Assertions.assertEquals(expectedPetOwner, out.setExtraDayOfProbation(ID, 0));

        expectedPetOwner.setDayOfProbation(25 - 8);

        Assertions.assertEquals(expectedPetOwner, out.setExtraDayOfProbation(ID, -8));
    }

    @Test
    public void testSayThatProbationIsOverSuccessfully() {
        when(petOwnerRepository.findById(ID)).thenReturn(Optional.of(new PetOwner()));

        Assertions.assertThrows(NotFoundException.class, () -> out.sayThatProbationIsOverSuccessfully(ID));

    }

    @Test
    public void testSayThatProbationIsOverNotSuccessfully() {
        when(petOwnerRepository.findById(ID)).thenReturn(Optional.of(new PetOwner()));

        Assertions.assertThrows(NotFoundException.class, () -> out.sayThatProbationIsOverNotSuccessfully(ID));
    }


}
