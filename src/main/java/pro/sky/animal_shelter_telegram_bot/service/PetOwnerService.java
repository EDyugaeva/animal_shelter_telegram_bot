package pro.sky.animal_shelter_telegram_bot.service;

import pro.sky.animal_shelter_telegram_bot.model.PetOwner;

import java.util.Collection;

public interface PetOwnerService {

    PetOwner addPetOwner(PetOwner petOwner);

    PetOwner deletePetOwner(Long id);

    PetOwner findPetOwner(Long id);

    PetOwner changePetOwner(PetOwner petOwner);

    String setPetOwnersPhoneNumber(String phoneNumber, Long id);

    Collection<PetOwner> getPetOwnerByDayOfProbation();

    Collection<PetOwner> getPetOwnerWithZeroDayOfProbation();

    PetOwner setPetOwnersName(String firstName, Long id);

    boolean petOwnerHasPhoneNumber(Long chatId);

    Long getPetOwnerChatIdByPhoneNumber(String phoneNumber);

    PetOwner findPetOwnerByChatId(Long chatId);

    PetOwner setExtraDayOfProbation(Long id, Integer extraDays);

    String sayThatProbationIsOverSuccessfully(Long id);

    String sayThatProbationIsOverNotSuccessfully(Long id);

    Collection<PetOwner> getAllPetOwners();
}
