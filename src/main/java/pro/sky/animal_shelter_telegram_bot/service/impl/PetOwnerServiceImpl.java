package pro.sky.animal_shelter_telegram_bot.service.impl;

import com.pengrad.telegrambot.model.Update;
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
    public void deletePetOwner(Long id) {
        petOwnerRepository.deleteById(id);
        logger.info("Pet owner with id {} is deleted", id);

    }

    @Override
    public PetOwner findPetOwner(Long id) {
        PetOwner findingPetOwner = petOwnerRepository.findById(id).get();
        if (findingPetOwner == null) {
            throw new NullPointerException("Pet Owner was not found ");
        }
        logger.info("Pet owner with id {} is found", id);
        return findingPetOwner;
    }

    @Override
    public PetOwner changePetOwner(PetOwner petOwner) {
        PetOwner changingPetOwner = petOwnerRepository.save(petOwner);
        logger.info("Pet owner {} is saved", petOwner);
        return changingPetOwner;
    }

    /**
     * add phone number to database from bot
     *
     * @param newPhoneNumber - String from update (message) from telegram
     * @param chatId - chat id fron update (telegram)
     * @return string message with phone number (how it was saved in database)
     * @throws NullPointerException - when message is empty
     * @throws IllegalArgumentException - when message has incorrect letters or symbols
     */
    @Override
    public String setPetOwnersPhoneNumber(String newPhoneNumber, Long chatId) {
        if (newPhoneNumber.isEmpty()) {
            logger.info("Phone number is empty");
            throw new NullPointerException("Phone number is empty");
        }
        String chars = "qwertyuiopasdfghjklzxcvbnm,./[];'{}#$%^&*";
        newPhoneNumber = newPhoneNumber.trim().replace("(", "").replace(")", "").replace("-", "");

        if (StringUtils.containsAny(newPhoneNumber, chars) || newPhoneNumber.length() > 11) {
            logger.info("Phone number is written with mistake");
            throw new IllegalArgumentException("Неправильно введен номер");
        }

        PetOwner petOwner = petOwnerRepository.findPetOwnerByChatId(chatId).orElse(new PetOwner());
        petOwner.setChatId(chatId);
        petOwner.setPhoneNumber(newPhoneNumber);
        logger.info("Номер {} записан", newPhoneNumber);
        petOwnerRepository.save(petOwner);
        return newPhoneNumber;
    }


}
