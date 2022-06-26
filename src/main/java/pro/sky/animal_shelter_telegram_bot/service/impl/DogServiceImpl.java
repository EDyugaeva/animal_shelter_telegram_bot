package pro.sky.animal_shelter_telegram_bot.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.sky.animal_shelter_telegram_bot.model.pets.Dog;
import pro.sky.animal_shelter_telegram_bot.service.DogService;

/**
 * Service for working with repository DogRepository
 */
public class DogServiceImpl implements DogService {

    Logger logger = LoggerFactory.getLogger(DogServiceImpl.class);

    private final DogRepository dogRepository;

    public DogServiceImpl(DogRepository dogRepository) {
        this.dogRepository = dogRepository;
    }

    @Override
    public Dog addDog(Dog dog) {
        Dog addingDog = dogRepository.save(dog);
        logger.info("Dog {} is saved", addingDog);
        return addingDog;
    }

    @Override
    public void deleteDog(Dog dog) {
        dogRepository.deleteById(dog.getId());
        logger.info("Dog {} is deleted", dog);
    }

    public void deleteDog(Long id) {
        dogRepository.deleteById(id);
        logger.info("Dog with id {} is deleted", id);
    }

    @Override
    public Dog findDog(Long id) {
        Dog findingDog = dogRepository.findById(id).get();
        logger.info("Dog with id {} is found", id);
        return findingDog;
    }

    @Override
    public Dog changeDog(Dog dog) {
        Dog changingDog = dogRepository.save(dog);
        logger.info("Dog {} is saved", dog);
        return changingDog;
    }
}
