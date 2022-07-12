package pro.sky.animal_shelter_telegram_bot.service;

import pro.sky.animal_shelter_telegram_bot.model.PetOwner;

public interface PetOwnerService {

    PetOwner addPetOwner(PetOwner petOwner);

    void deletePetOwner(PetOwner petOwner);

    boolean deletePetOwner(Long id);

    PetOwner findPetOwner(Long id);

    PetOwner changePetOwner(PetOwner petOwner);

    String setPetOwnersPhoneNumber(String phoneNumber, Long id);
}
