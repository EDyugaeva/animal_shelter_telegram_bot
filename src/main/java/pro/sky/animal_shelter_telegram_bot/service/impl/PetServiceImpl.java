package pro.sky.animal_shelter_telegram_bot.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.animal_shelter_telegram_bot.model.pets.Pet;
import pro.sky.animal_shelter_telegram_bot.repository.PetRepository;
import pro.sky.animal_shelter_telegram_bot.service.PetService;

/**
 * Service for working with repository DogRepository
 */
@Service
public class PetServiceImpl implements PetService {

    Logger logger = LoggerFactory.getLogger(PetServiceImpl.class);

    private final PetRepository petRepository;

    public PetServiceImpl(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Override
    public Pet addPet(Pet pet) {
        Pet addingPet = petRepository.save(pet);
        logger.info("Pet {} is saved", addingPet);
        return addingPet;
    }

    @Override
    public void deletePet(Pet pet) {
        petRepository.deleteById(pet.getId());
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
        Pet changingPet = petRepository.save(pet);
        logger.info("Pet {} is saved", pet);
        return changingPet;
    }
}
