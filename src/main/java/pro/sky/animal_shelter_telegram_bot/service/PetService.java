package pro.sky.animal_shelter_telegram_bot.service;

import pro.sky.animal_shelter_telegram_bot.model.pets.Pet;

public interface PetService {

    Pet addPet(Pet pet);

    void deletePet(Pet pet);

    void deletePet(Long id);

    Pet findPet(Long id);

    Pet changePet(Pet pet);
}
