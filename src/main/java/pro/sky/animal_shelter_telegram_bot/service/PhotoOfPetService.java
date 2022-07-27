package pro.sky.animal_shelter_telegram_bot.service;

import com.pengrad.telegrambot.model.File;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.animal_shelter_telegram_bot.model.pets.PhotoOfPet;

import java.io.IOException;

public interface PhotoOfPetService {

    void uploadPhotoOfPet(Long reportId, MultipartFile avatarFile) throws IOException;

    PhotoOfPet findPhotoByReportId(Long reportId);

    void uploadPhotoFromTg(Long chatId, byte[] data, File file, String date) throws IOException;

}
