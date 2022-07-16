package pro.sky.animal_shelter_telegram_bot.service;

import com.pengrad.telegrambot.model.Update;
import pro.sky.animal_shelter_telegram_bot.model.PetOwner;

import java.util.Collection;

public interface PetOwnerService {

    PetOwner addPetOwner(PetOwner petOwner);

    void deletePetOwner(PetOwner petOwner);

    void deletePetOwner(Long id);

    PetOwner findPetOwner(Long id);

    PetOwner changePetOwner(PetOwner petOwner);

    String setPetOwnersPhoneNumber(String phoneNumber, Long id);

    Collection<PetOwner> getPetOwnerByDayOfProbation();

    Collection<PetOwner> getPetOwnerWithZeroDayOfProbation();

}
