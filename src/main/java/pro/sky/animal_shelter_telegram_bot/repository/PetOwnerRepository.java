package pro.sky.animal_shelter_telegram_bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.animal_shelter_telegram_bot.model.PetOwner;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface PetOwnerRepository extends JpaRepository<PetOwner, Long> {

    void deleteById(Long id);

    Collection<PetOwner> findPetOwnerById(Long id);

    Optional<PetOwner> findPetOwnerByChatId(Long chatId);

}
