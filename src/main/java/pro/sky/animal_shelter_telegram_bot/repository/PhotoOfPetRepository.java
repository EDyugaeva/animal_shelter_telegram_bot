package pro.sky.animal_shelter_telegram_bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.animal_shelter_telegram_bot.model.pets.PhotoOfPet;

import java.util.Optional;

@Repository
public interface PhotoOfPetRepository extends JpaRepository<PhotoOfPet, Long> {

    void deleteById(Long id);

    Optional<PhotoOfPet> findPhotoOfPetByReportId(Long reportId);
}
