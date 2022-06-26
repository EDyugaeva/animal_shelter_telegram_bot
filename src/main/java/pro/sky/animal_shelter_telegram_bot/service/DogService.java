package pro.sky.animal_shelter_telegram_bot.service;

import pro.sky.animal_shelter_telegram_bot.model.pets.Dog;

public interface DogService {

    Dog addDog(Dog dog);

    void deleteDog(Dog dog);

    void deleteDog(Long id);

    Dog findDog(Long id);

    Dog changeDog(Dog dog);
}
