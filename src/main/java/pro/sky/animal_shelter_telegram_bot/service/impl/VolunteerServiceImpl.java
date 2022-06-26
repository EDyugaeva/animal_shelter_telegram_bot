package pro.sky.animal_shelter_telegram_bot.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.sky.animal_shelter_telegram_bot.model.PetOwner;
import pro.sky.animal_shelter_telegram_bot.model.Volunteer;
import pro.sky.animal_shelter_telegram_bot.service.VolunteerService;

/**
 * Service for working with repository VolunteerRepository
 */
public class VolunteerServiceImpl implements VolunteerService {

    Logger logger = LoggerFactory.getLogger(VolunteerServiceImpl.class);

    private final VolonteerRepository volonteerRepository;

    public VolunteerServiceImpl(VolonteerRepository volonteerRepository) {
        this.volonteerRepository = volonteerRepository;
    }


    @Override
    public Volunteer addVolunteer(Volunteer volunteer) {
        Volunteer addingVolunteer = VolonteerRepository.save(volunteer);
        logger.info("Volunteer {} is saved", addingVolunteer);
        return addingVolunteer;
    }

    @Override
    public void deleteVolunteer(Volunteer volunteer) {
        VolonteerRepository.deleteById(volunteer.getId());
        logger.info("Volunteer {} is deleted", volunteer);
    }

    @Override
    public void deleteVolunteer(Long id) {
        VolonteerRepository.deleteById(id);
        logger.info("Volunteer with id {} is deleted", id);

    }

    @Override
    public Volunteer findVolunteer(Long id) {
        Volunteer findingVolunteer = VolonteerRepository.findById(id).get();
        logger.info("Volunteer with id {} is found", id);
        return findingVolunteer;
    }

    @Override
    public Volunteer changeVolunteer(Volunteer volunteer) {
        Volunteer changingVolunteer = VolonteerRepository.save(volunteer);
        logger.info("Volunteer {} is saved", volunteer);
        return changingVolunteer;
    }

    /**
     * add phone number to database
     *
     * @param phoneNumber - phone number from swagger
     * @param id          - volunteer id, positive
     * @return
     */
    @Override
    public Volunteer setVolunteersPhoneNumber(String phoneNumber, Long id) {
        if (phoneNumber.isEmpty()) {
            logger.info("Phone number is empty");
            throw new NullPointerException("Phone number is empty");
        }
        Volunteer changingVolunteer = VolonteerRepository.findById(id);
        if (changingVolunteer == null) {
            logger.info("Volunteer was not found");
            throw new NullPointerException("Volunteer was not found");
        }
        changingVolunteer.setPhoneNumber(phoneNumber);
        logger.info("Volunteer {} is changed. Phone number {} is added.", changingPetOwner + phoneNumber);
        return VolonteerRepository.save(changingVolunteer);
    }


}
