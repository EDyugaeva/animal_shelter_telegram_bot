package pro.sky.animal_shelter_telegram_bot.service.impl;

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
     * add phone number to database
     *
     * @param phoneNumber - phone number from telegram
     * @param id          - pet owner id, positive
     * @return
     */
    @Override
    public PetOwner setPetOwnersPhoneNumber(String phoneNumber, Long id) {
        if (phoneNumber.isEmpty()) {
            logger.info("Phone number is empty");
            throw new NullPointerException("Phone number is empty");
        }
        PetOwner changingPetOwner = petOwnerRepository.findById(id).orElse(new PetOwner());
        if (changingPetOwner == null) {
            logger.info("Pet Owner was not found, it was created");
        }
        changingPetOwner.setPhoneNumber(phoneNumber);
        logger.info("Pet owner {} is changed. Phone number {} is added.", changingPetOwner + phoneNumber);
        return petOwnerRepository.save(changingPetOwner);
    }


}
