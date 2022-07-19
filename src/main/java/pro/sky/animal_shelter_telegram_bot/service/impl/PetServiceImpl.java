package pro.sky.animal_shelter_telegram_bot.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.animal_shelter_telegram_bot.model.pets.Pet;
import pro.sky.animal_shelter_telegram_bot.repository.PetRepository;
import pro.sky.animal_shelter_telegram_bot.service.PetService;

import java.util.Collection;

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
        logger.info("Pet with id {} is saved", addingPet.getId());
        return addingPet;
    }

    public Pet deletePet(Long id) {
        if (petRepository.findById(id).isEmpty()){
            logger.info("Pet with id {} is not found", id);
            return null;
        }
        Pet deletePet = petRepository.findById(id).get();
        petRepository.deleteById(id);
        logger.info("Pet with id {} is deleted", id);
        return deletePet;
    }

    @Override
    public Pet findPet(Long id) {
        if (petRepository.findById(id).isEmpty()){
            logger.info("Pet with id {} is not found", id);
            return null;
        }
        Pet findingPet = petRepository.findById(id).get();
        logger.info("Pet with id {} is found", id);
        return findingPet;
    }

    @Override
    public Pet changePet(Pet pet) {
        if (petRepository.findById(pet.getId()).isEmpty()){
            logger.info("Pet with id {} is not found", pet.getId());
            return null;
        }
        Pet changingPet = petRepository.save(pet);
        logger.info("Pet with id {} is saved", pet);
        return changingPet;
    }

    /**
     * find pets in database
     *
     * @return Collection of Pet
     */
    @Override
    public Collection<Pet> getAllPets() {
        logger.info("Was invoked method for getAllPets");
        return petRepository.findAll();
    }
}
