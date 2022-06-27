package pro.sky.animal_shelter_telegram_bot.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.sky.animal_shelter_telegram_bot.model.pets.Dog;
import pro.sky.animal_shelter_telegram_bot.model.pets.Pet;
import pro.sky.animal_shelter_telegram_bot.service.PetService;

/**
 * Service for working with repository DogRepository
 */
public class PetServiceImpl implements PetService {

    Logger logger = LoggerFactory.getLogger(PetServiceImpl.class);

    private final PetRepository petRepository;

    public PetServiceImpl(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Override
    public Pet addPet(Pet pet) {
        Dog addingPet = petRepository.save(pet);
        logger.info("Pet {} is saved", addingPet);
        return addingPet;
    }

    @Override
    public void deletePet(Pet pet) {
        petRepository.deleteById(dog.getId());
        logger.info("Pet {} is deleted", pet);
    }

    public void deletePet(Long id) {
        petRepository.deleteById(id);
        logger.info("Pet with id {} is deleted", id);
    }

    @Override
    public Pet findPet(Long id) {
        Pet findingPet = petRepository.findById(id).get();
        logger.info("Pet with id {} is found", id);
        return findingPet;
    }

    @Override
    public Pet changePet(Pet pet) {
        Dog changingPet = petRepository.save(pet);
        logger.info("Pet {} is saved", pet);
        return changingPet;
    }
}
