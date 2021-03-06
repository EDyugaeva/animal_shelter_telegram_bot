package pro.sky.animal_shelter_telegram_bot.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.animal_shelter_telegram_bot.model.Volunteer;
import pro.sky.animal_shelter_telegram_bot.repository.VolunteerRepository;
import pro.sky.animal_shelter_telegram_bot.service.VolunteerService;

/**
 * Service for working with repository VolunteerRepository
 */
@Service
public class VolunteerServiceImpl implements VolunteerService {

    Logger logger = LoggerFactory.getLogger(VolunteerServiceImpl.class);

    private final VolunteerRepository volunteerRepository;

    public VolunteerServiceImpl(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
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
    public void deleteVolunteer(Long id) {
        volunteerRepository.deleteById(id);
        logger.info("Volunteer with id {} is deleted", id);

    }

    @Override
    public Volunteer findVolunteer(Long id) {
        Volunteer findingVolunteer = volunteerRepository.findById(id).get();
        logger.info("Volunteer with id {} is found", id);
        return findingVolunteer;
    }

    @Override
    public Volunteer changeVolunteer(Volunteer volunteer) {
        Volunteer changingVolunteer = volunteerRepository.save(volunteer);
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
        Volunteer changingVolunteer = volunteerRepository.findById(id).orElse(new Volunteer());
        if (changingVolunteer == null) {
            logger.info("Volunteer was not found, created empty Volunteer");
        }
        changingVolunteer.setPhoneNumber(phoneNumber);
        logger.info("Volunteer {} is changed. Phone number {} is added.", changingVolunteer + phoneNumber);
        return volunteerRepository.save(changingVolunteer);
    }
}
