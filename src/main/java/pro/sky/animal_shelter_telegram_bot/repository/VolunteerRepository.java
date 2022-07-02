package pro.sky.animal_shelter_telegram_bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.animal_shelter_telegram_bot.model.Volunteer;

import java.util.Collection;

@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {

    void deleteById(Long id);

    Collection<Volunteer> findVolunteerById(Long id);

}
