package pro.sky.animal_shelter_telegram_bot.service;

import pro.sky.animal_shelter_telegram_bot.model.Volunteer;

public interface VolunteerService {

    Volunteer addVolunteer(Volunteer volunteer);

    void deleteVolunteer(Volunteer volunteer);

    boolean deleteVolunteer(Long id);

    Volunteer findVolunteer(Long id);

    Volunteer changeVolunteer(Volunteer volunteer);

    Volunteer setVolunteersPhoneNumber(String phoneNumber, Long id);
}
