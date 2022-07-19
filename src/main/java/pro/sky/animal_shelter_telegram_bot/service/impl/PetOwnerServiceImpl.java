package pro.sky.animal_shelter_telegram_bot.service.impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import pro.sky.animal_shelter_telegram_bot.model.PetOwner;
import pro.sky.animal_shelter_telegram_bot.repository.PetOwnerRepository;
import pro.sky.animal_shelter_telegram_bot.service.PetOwnerService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Service for working with repository PetOwnerRepository
 */
@Service
public class PetOwnerServiceImpl implements PetOwnerService {

    Logger logger = LoggerFactory.getLogger(PetOwnerServiceImpl.class);

    private final TelegramBot telegramBot;

    private final PetOwnerRepository petOwnerRepository;

    public PetOwnerServiceImpl(TelegramBot telegramBot, PetOwnerRepository petOwnerRepository) {
        this.telegramBot = telegramBot;
        this.petOwnerRepository = petOwnerRepository;
    }

    @Override
    public PetOwner addPetOwner(PetOwner petOwner) {
        PetOwner addingPetOwner = petOwnerRepository.save(petOwner);
        logger.info("Pet owner {} is saved", addingPetOwner);
        return addingPetOwner;
    }

    @Override
    public PetOwner deletePetOwner(Long id) {
        if (petOwnerRepository.findById(id).isEmpty()) {
            logger.info("Pet owner with id {} is not found", id);
            return null;
        }
        PetOwner deletePetOwner = petOwnerRepository.findById(id).get();
        petOwnerRepository.deleteById(id);
        logger.info("Pet owner with id {} is deleted", id);
        return deletePetOwner;
    }

    @Override
    public PetOwner findPetOwner(Long id) {
        if (petOwnerRepository.findById(id).isEmpty()) {
            logger.info("Pet owner with id {} is not found", id);
            return null;
        }
        PetOwner petOwner = petOwnerRepository.findById(id).get();
        logger.info("Pet owner with id {} is found", id);
        return petOwner;
    }

    @Override
    public PetOwner changePetOwner(PetOwner petOwner) {
        if (petOwnerRepository.findById(petOwner.getId()).isEmpty()) {
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
     * @throws NullPointerException - when message is empty
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
        logger.info("Номер {} записан", newPhoneNumber);
        petOwnerRepository.save(petOwner);
        return newPhoneNumber;
    }

    @Override
    public Collection<PetOwner> getPetOwnerByDayOfProbation() {
        List<PetOwner> petOwnersList = new ArrayList<>(petOwnerRepository.getPetOwnerByDayOfProbation());
        if (petOwnersList.isEmpty()) {
            logger.error("Pet owner list with day of probation > 0 is empty");
            throw new NotFoundException("All pet owner do not have probation");
        }
        logger.info("Get list of pet owners with days of probation more then zero");
        return petOwnersList;
    }

    /**
     * @return list of volunteers with ending of probation
     */
    @Override
    public Collection<PetOwner> getPetOwnerWithZeroDayOfProbation() {
        List<PetOwner> petOwnersList = new ArrayList<>(petOwnerRepository.getPetOwnerWithZeroDayOfProbation());
        if (petOwnersList.isEmpty()) {
            logger.error("Pet owner list with day of probation = 0 is empty");
            throw new NotFoundException("All pet owner have a long probation period");
        }
        logger.info("Get list of pet owners with days of probation equal zero");
        return petOwnersList;
    }


    /**
     * Add name to database ONLY from bot (setting day of probation = -1)
     *
     * @param name - String from update (message) from telegram
     * @param id   - chat id from update (telegram)
     * @return string message with name
     */
    @Override
    public PetOwner setPetOwnersName(String name, Long id) {
        if (name.isEmpty()) {
            logger.info("Name is empty");
            throw new NullPointerException("Name is empty");
        }
        PetOwner petOwner = petOwnerRepository.findPetOwnerByChatId(id).orElse(new PetOwner());
        petOwner.setChatId(id);
        petOwner.setFirstName(name);
        petOwner.setDayOfProbation(-1);
        petOwnerRepository.save(petOwner);

        logger.info("Name {} is saved", name);

        return petOwner;
    }

    /**
     * @param chatId - chat id
     * @return true if pet owner has name
     */
    @Override
    public boolean petOwnerHasPhoneNumber(Long chatId) {
        PetOwner petOwner = petOwnerRepository.findPetOwnerByChatId(chatId).orElse(new PetOwner());
        if (petOwner.getPhoneNumber() != null) return true;
        return false;
    }

    /**
     * @param id - chat id from telegram
     * @return Pet owner or new Pet owner
     */
    @Override
    public PetOwner findPetOwnerByChatId(Long id) {
        PetOwner findingPetOwner = petOwnerRepository.findPetOwnerByChatId(id).orElse(new PetOwner());
        logger.info("Pet owner with chat id {} is found", id);
        return findingPetOwner;
    }

    /**
     * find pet owner with phone number
     *
     * @param phoneNumber - texted phone number (+7...)
     * @return chat id or NullPointerException
     */
    @Override
    public Long getPetOwnerChatIdByPhoneNumber(String phoneNumber) {
        PetOwner petOwner = petOwnerRepository.findPetOwnerByPhoneNumber(phoneNumber).get();
        if (petOwner.getChatId() != null) {
            return petOwner.getChatId();
        }
        throw new NullPointerException("Pet Owner does not exist");
    }

    /**
     * add or reduce probation
     *
     * @param id        - petOwner id
     * @param extraDays - amount of day, which could be added (or reduced)
     * @return pet owner
     */
    @Override
    public PetOwner setExtraDayOfProbation(Long id, Integer extraDays) {
        PetOwner petOwner = petOwnerRepository.findById(id).orElse(new PetOwner());
        if (petOwner.getId() == null) {
            logger.warn("Pet owner with id {} was not found", id);
            throw new NotFoundException("Pet owner was not found");
        }
        petOwner.setDayOfProbation(petOwner.getDayOfProbation() + extraDays);
        petOwnerRepository.save(petOwner);

        return petOwner;
    }

    @Override
    public String sayThatProbationIsOverSuccessfully(Long id) {
        PetOwner petOwner = petOwnerRepository.findById(id).orElse(new PetOwner());
        if (petOwner.getId() == null) {
            logger.warn("Pet owner with id {} was not found", id);
            throw new NotFoundException("Pet owner was not found");
        }
        Long chatId = petOwner.getChatId();
        String message = "Поздравляем! Ваш испытательный срок прошел успешно!";
        sendMessage(chatId, message);
        petOwner.setDayOfProbation(-1);
        petOwnerRepository.save(petOwner);
        return "Испытательный срок усыновителя с id = " + id + " прошел успешно!";
    }

    @Override
    public String sayThatProbationIsOverNotSuccessfully(Long id) {
        PetOwner petOwner = petOwnerRepository.findById(id).orElse(new PetOwner());
        if (petOwner.getId() == null) {
            logger.warn("Pet owner with id {} was not found", id);
            throw new NotFoundException("Pet owner was not found");
        }
        Long chatId = petOwner.getChatId();
        String message = "Вы не прошли испытательный срок. С вами свяжется волонтер и объяснит, как действовать дальше";
        sendMessage(chatId, message);
        petOwner.setDayOfProbation(-100);
        petOwnerRepository.save(petOwner);
        petOwnerRepository.save(petOwner);
        return "Испытательный срок усыновителя с id = " + id + " прошел не успешно!. Свяжитесь с ним по номеру : " + petOwner.getPhoneNumber();
    }

    /**
     * find petOwners in database
     *
     * @return Collection of PetOwners
     */
    @Override
    public Collection<PetOwner> getAllPetOwners() {
        logger.info("Was invoked method for getAllPetOwners");
        return petOwnerRepository.findAll();
    }


    private void sendMessage(Long chatId, String message) {
        SendResponse response = telegramBot.execute(new SendMessage(chatId, message));
        if (response.isOk()) {
            logger.info("message: {} is sent ", message);
        } else {
            logger.warn("Message was not sent.  " + response.description());
        }
    }
}
