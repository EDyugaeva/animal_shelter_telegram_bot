package pro.sky.animal_shelter_telegram_bot.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.test.context.TestPropertySource;
import org.webjars.NotFoundException;
import pro.sky.animal_shelter_telegram_bot.model.PetOwner;
import pro.sky.animal_shelter_telegram_bot.repository.PetOwnerRepository;
import pro.sky.animal_shelter_telegram_bot.service.impl.PetOwnerServiceImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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

        Assertions.assertThrows(NullPointerException.class, () -> out.setPetOwnersPhoneNumber("", id));


    }

    @Test
    public void testGetPetOwnerByDayOfProbation() {

        Collection<PetOwner> collectionPetOwner = new ArrayList<>();
        when(petOwnerRepository.getPetOwnerByDayOfProbation()).thenReturn(collectionPetOwner);

        Assertions.assertThrows(NotFoundException.class, () -> out.getPetOwnerByDayOfProbation());
        PetOwner petOwner1 = new PetOwner();
        PetOwner petOwner2 = new PetOwner();

        petOwner1.setDayOfProbation(5);
        petOwner1.setDayOfProbation(2);

        collectionPetOwner.add(petOwner1);
        collectionPetOwner.add(petOwner2);

        Collection<PetOwner> expectedCollectionPetOwner = new ArrayList<>();
        expectedCollectionPetOwner.add(petOwner1);
        expectedCollectionPetOwner.add(petOwner2);

        Assertions.assertEquals(expectedCollectionPetOwner, out.getPetOwnerByDayOfProbation());

    }

    @Test
    public void testGetPetOwnerByZeroDayOfProbation() {

        Collection<PetOwner> collectionPetOwner = new ArrayList<>();
        when(petOwnerRepository.getPetOwnerByDayOfProbation()).thenReturn(collectionPetOwner);

        Assertions.assertThrows(NotFoundException.class, () -> out.getPetOwnerByDayOfProbation());
        PetOwner petOwner1 = new PetOwner();
        PetOwner petOwner2 = new PetOwner();

        petOwner1.setDayOfProbation(0);
        petOwner1.setDayOfProbation(0);

        collectionPetOwner.add(petOwner1);
        collectionPetOwner.add(petOwner2);

        Collection<PetOwner> expectedCollectionPetOwner = new ArrayList<>();
        expectedCollectionPetOwner.add(petOwner1);
        expectedCollectionPetOwner.add(petOwner2);

        Assertions.assertEquals(expectedCollectionPetOwner, out.getPetOwnerByDayOfProbation());

    }

    @Test
    public void testSetOwnersName() {
        Long chatId = 1L;
        PetOwner petOwner = new PetOwner();
        petOwner.setId(chatId);
        String name = "name";

        when(petOwnerRepository.findPetOwnerByChatId(chatId)).thenReturn(Optional.of(petOwner));

        Assertions.assertThrows(NullPointerException.class, () -> out.setPetOwnersName("", chatId));

        PetOwner expectedPetOwner = petOwner;
        expectedPetOwner.setFirstName(name);

        Assertions.assertEquals(expectedPetOwner, out.setPetOwnersName(name, chatId));

    }

    @Test
    public void testGetPetOwnerChatIdByPhoneNumber() {
        String phoneNumber = "+798989898";
        Long chatId = 1L;
        PetOwner petOwner = new PetOwner();
        petOwner.setPhoneNumber(phoneNumber);

        when(petOwnerRepository.findPetOwnerByPhoneNumber(phoneNumber)).thenReturn(Optional.of(petOwner));

        Assertions.assertThrows(NullPointerException.class, () -> out.getPetOwnerChatIdByPhoneNumber(phoneNumber));
        petOwner.setChatId(1L);

        Assertions.assertEquals(chatId, out.getPetOwnerChatIdByPhoneNumber(phoneNumber));
    }

    @Test
    public void testSetExtraDayOfProbation() {
        Long id = 1L;
        PetOwner petOwner = new PetOwner();
        petOwner.setDayOfProbation(25);

        when(petOwnerRepository.findById(id)).thenReturn(Optional.of(petOwner));

        Assertions.assertThrows(NotFoundException.class, () -> out.setExtraDayOfProbation(id, 5));

        petOwner.setId(id);
        PetOwner expectedPetOwner = petOwner;
        expectedPetOwner.setDayOfProbation(25+5);

        Assertions.assertEquals(expectedPetOwner, out.setExtraDayOfProbation(id,5));

        expectedPetOwner.setDayOfProbation(25+0);

        Assertions.assertEquals(expectedPetOwner, out.setExtraDayOfProbation(id,0));

        expectedPetOwner.setDayOfProbation(25-8);

        Assertions.assertEquals(expectedPetOwner, out.setExtraDayOfProbation(id,-8));
    }

    @Test
    public void testSayThatProbationIsOverSuccessfully() {
        Long id = 1L;

        when(petOwnerRepository.findById(id)).thenReturn(Optional.of(new PetOwner()));

        Assertions.assertThrows(NotFoundException.class, () -> out.sayThatProbationIsOverSuccessfully(id));

    }

    @Test
    public void testSayThatProbationIsOverNotSuccessfully() {
        Long id = 1L;

        when(petOwnerRepository.findById(id)).thenReturn(Optional.of(new PetOwner()));

        Assertions.assertThrows(NotFoundException.class, () -> out.sayThatProbationIsOverNotSuccessfully(id));

    }




}
