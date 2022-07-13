package pro.sky.animal_shelter_telegram_bot.service.impl;

import liquibase.repackaged.org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.animal_shelter_telegram_bot.model.PetOwner;
import pro.sky.animal_shelter_telegram_bot.repository.PetOwnerRepository;
import pro.sky.animal_shelter_telegram_bot.service.PetOwnerService;

/**
 * Service for working with repository PetOwnerRepository
 */
@Service
public class PetOwnerServiceImpl implements PetOwnerService {

    Logger logger = LoggerFactory.getLogger(PetOwnerServiceImpl.class);

    private final PetOwnerRepository petOwnerRepository;

    public PetOwnerServiceImpl(PetOwnerRepository petOwnerRepository) {
        this.petOwnerRepository = petOwnerRepository;
    }

    @Override
    public PetOwner addPetOwner(PetOwner petOwner) {
        PetOwner addingPetOwner = petOwnerRepository.save(petOwner);
        logger.info("Pet owner {} is saved", addingPetOwner);
        return addingPetOwner;
    }

    @Override
    public void deletePetOwner(PetOwner petOwner) {
        petOwnerRepository.deleteById(petOwner.getId());
        logger.info("Pet owner {} is deleted", petOwner);

    }

    @Override
    public boolean deletePetOwner(Long id) {
        if (petOwnerRepository.findById(id).isEmpty()){
            logger.info("Pet owner with id {} is not found", id);
            return false;
        }
        petOwnerRepository.deleteById(id);
        logger.info("Pet owner with id {} is deleted", id);
        return true;
    }

    @Override
    public PetOwner findPetOwner(Long id) {
        if (petOwnerRepository.findById(id).isEmpty()){
            logger.info("Pet owner with id {} is not found", id);
            return null;
        }
        PetOwner petOwner = petOwnerRepository.findById(id).get();
        logger.info("Pet owner with id {} is found", id);
        return petOwner;
    }

    @Override
    public PetOwner changePetOwner(PetOwner petOwner) {
        if (petOwnerRepository.findById(petOwner.getId()).isEmpty()){
            logger.info("Pet owner with id {} is not found", petOwner.getId());
            return null;
        }
        PetOwner changingPetOwner = petOwnerRepository.save(petOwner);
        logger.info("Pet owner with id {} is saved", petOwner);
        return changingPetOwner;
    }

    /**
     * add phone number to database from bot
     *
     * @param newPhoneNumber - String from update (message) from telegram
     * @param chatId         - chat id from update (telegram)
     * @return string message with phone number (how it was saved in database)
     * @throws NullPointerException     - when message is empty
     */
    @Override
    public String setPetOwnersPhoneNumber(String newPhoneNumber, Long chatId) {
        if (newPhoneNumber.isEmpty()) {
            logger.info("Phone number is empty");
            throw new NullPointerException("Phone number is empty");
        }
        PetOwner petOwner = petOwnerRepository.findPetOwnerByChatId(chatId).orElse(new PetOwner());
        petOwner.setChatId(chatId);
        petOwner.setPhoneNumber(newPhoneNumber);
        logger.info("Phone number {} is saved", newPhoneNumber);
        petOwnerRepository.save(petOwner);
        return newPhoneNumber;
    }

    /**
     * Add name to database from bot
     *
     * @param name - String from update (message) from telegram
     * @param id   - chat id fron update (telegram)
     * @return string message with name
     */
    @Override
    public String setPetOwnersName(String name, Long id) {
        if (name.isEmpty()) {
            logger.info("Name is empty");
            throw new NullPointerException("Name is empty");
        }
        PetOwner petOwner = petOwnerRepository.findPetOwnerByChatId(id).orElse(new PetOwner());
        petOwner.setChatId(id);
        petOwner.setFirstName(name);
        logger.info("Name {} is saved", name);
        petOwnerRepository.save(petOwner);

        return name;
    }

    /**
     * @param id - chat id
     * @return true if pet owner has name
     */
    @Override
    public boolean petOwnerHasName(Long id) {
        PetOwner petOwner = petOwnerRepository.findPetOwnerByChatId(id).orElse(new PetOwner());
        if (petOwner.getFirstName() != null) {
            return true;
        }
        return false;
    }

    /**
     * @param id - chat id from telegram
     * @return Pet owner or new Pet owner
     */
    @Override
    public PetOwner findPetOwnerByChatId(Long id) {
        PetOwner findingPetOwner = petOwnerRepository.findPetOwnerByChatId(id).get();

        logger.info("Pet owner with chat id {} is found", id);
        return findingPetOwner;
    }


}
