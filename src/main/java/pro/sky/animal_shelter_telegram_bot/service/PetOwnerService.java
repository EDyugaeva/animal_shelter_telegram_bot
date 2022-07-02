package pro.sky.animal_shelter_telegram_bot.service;

import pro.sky.animal_shelter_telegram_bot.model.PetOwner;

public interface PetOwnerService {

    PetOwner addPetOwner(PetOwner petOwner);

    void deletePetOwner(PetOwner petOwner);

    void deletePetOwner(Long id);

    PetOwner findPetOwner(Long id);

    PetOwner changePetOwner(PetOwner petOwner);

    PetOwner setPetOwnersPhoneNumber(String phoneNumber, Long id);
}
