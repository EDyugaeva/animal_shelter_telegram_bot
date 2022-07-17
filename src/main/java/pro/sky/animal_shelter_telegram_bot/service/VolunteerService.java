package pro.sky.animal_shelter_telegram_bot.service;

import pro.sky.animal_shelter_telegram_bot.model.Report;
import pro.sky.animal_shelter_telegram_bot.model.Volunteer;

import java.util.List;

public interface VolunteerService {

    Volunteer addVolunteer(Volunteer volunteer);

    void deleteVolunteer(Volunteer volunteer);

    boolean deleteVolunteer(Long id);

    Volunteer findVolunteer(Long id);

    Volunteer changeVolunteer(Volunteer volunteer);

    Volunteer setVolunteersPhoneNumber(Volunteer volunteer, String phoneNumber);

    Volunteer findVolunteerByPhoneNumber(String phoneNumber);

    List<Volunteer> findAllVolunteer();

}
