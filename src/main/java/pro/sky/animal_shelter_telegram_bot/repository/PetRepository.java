package pro.sky.animal_shelter_telegram_bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.animal_shelter_telegram_bot.model.pets.Pet;

import java.util.Collection;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    void deleteById(Long id);

    Collection<Pet> findPetById(Long id);

}

