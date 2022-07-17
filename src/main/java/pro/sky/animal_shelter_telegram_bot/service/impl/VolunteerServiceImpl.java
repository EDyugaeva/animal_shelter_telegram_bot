package pro.sky.animal_shelter_telegram_bot.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import pro.sky.animal_shelter_telegram_bot.model.Volunteer;
import pro.sky.animal_shelter_telegram_bot.repository.VolunteerRepository;
import pro.sky.animal_shelter_telegram_bot.service.PetOwnerService;
import pro.sky.animal_shelter_telegram_bot.service.VolunteerService;

import java.util.List;

/**
 * Service for working with repository VolunteerRepository
 */
@Service
public class VolunteerServiceImpl implements VolunteerService {

    Logger logger = LoggerFactory.getLogger(VolunteerServiceImpl.class);

    private final VolunteerRepository volunteerRepository;

    private final PetOwnerService petOwnerService;

    public VolunteerServiceImpl(VolunteerRepository volunteerRepository, PetOwnerServiceImpl petOwnerService) {
        this.volunteerRepository = volunteerRepository;
        this.petOwnerService = petOwnerService;
    }

    @Override
    public Volunteer addVolunteer(Volunteer volunteer) {
        Volunteer addingVolunteer = volunteerRepository.save(volunteer);
        logger.info("Volunteer {} is saved", addingVolunteer);
        return addingVolunteer;
    }

    @Override
    public void deleteVolunteer(Volunteer volunteer) {
        volunteerRepository.deleteById(volunteer.getId());
        logger.info("Volunteer {} is deleted", volunteer);
    }

    @Override
    public boolean deleteVolunteer(Long id) {
        if (volunteerRepository.findById(id).isEmpty()) {
            logger.info("Volunteer with id {} is not found", id);
            return false;
        }
        volunteerRepository.deleteById(id);
        logger.info("Volunteer with id {} is deleted", id);
        return true;
    }

    @Override
    public Volunteer findVolunteer(Long id) {
        if (volunteerRepository.findById(id).isEmpty()) {
            logger.info("Volunteer with id {} is not found", id);
            return null;
        }
        Volunteer volunteer = volunteerRepository.findById(id).get();
        logger.info("Volunteer with id {} is found", id);
        return volunteer;
    }


    @Override
    public Volunteer changeVolunteer(Volunteer volunteer) {
        if (volunteerRepository.findById(volunteer.getId()).isEmpty()) {
            logger.info("Volunteer with id {} is not found", volunteer.getId());
            return null;
        }
        Volunteer changingVolunteer = volunteerRepository.save(volunteer);
        logger.info("Volunteer with id {} is saved", volunteer);
        return changingVolunteer;
    }



    /**
     * add phone number to database. If this phone number was in pet-owner table, chat id will be saved to this table
     *
     * @param phoneNumber - phone number from swagger
     * @param volunteer   - volunteer
       */
    @Override
    public Volunteer setVolunteersPhoneNumber(Volunteer volunteer, String phoneNumber) {
        if (phoneNumber.isEmpty()) {
            logger.info("Phone number is empty");
            throw new NullPointerException("Phone number is empty");
        }
        volunteer.setPhoneNumber(phoneNumber);
        try {
            volunteer.setChatId(petOwnerService.getPetOwnerChatIdByPhoneNumber(phoneNumber));
        } catch (NullPointerException e) {
            logger.info("Error");
        }

        logger.info("Volunteer {} is changed. Phone number {} is added.", volunteer, phoneNumber);
        return volunteerRepository.save(volunteer);
    }

    /**
     * find volunteer in database
     * @param phoneNumber
     * @return volunteer
     */
    @Override
    public Volunteer findVolunteerByPhoneNumber(String phoneNumber) {
        return volunteerRepository.findVolunteerByPhoneNumber(phoneNumber);
    }

    /**
     *
     * @return list of volunteers
     */
    @Override
    public List<Volunteer> findAllVolunteer() {
        List<Volunteer> volunteerList = volunteerRepository.findAll();
        if (volunteerList.isEmpty()) {
            logger.error("Volunteer list is empty");
            throw new NotFoundException("Volunteer are empty");
        }
        return volunteerList;
    }
}
