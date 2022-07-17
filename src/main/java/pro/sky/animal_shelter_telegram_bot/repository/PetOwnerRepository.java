package pro.sky.animal_shelter_telegram_bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pro.sky.animal_shelter_telegram_bot.model.PetOwner;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface PetOwnerRepository extends JpaRepository<PetOwner, Long> {

    void deleteById(Long id);

    Optional<PetOwner> findPetOwnerByChatId(Long chatId);

    @Query(value = "SELECT * FROM pet_owner WHERE day_of_probation > 0", nativeQuery = true)
    Collection<PetOwner> getPetOwnerByDayOfProbation();

    @Query(value = "SELECT * FROM pet_owner WHERE day_of_probation = 0", nativeQuery = true)
    Collection<PetOwner> getPetOwnerWithZeroDayOfProbation();

    Optional<PetOwner> findPetOwnerByPhoneNumber(String phoneNumber);
}
