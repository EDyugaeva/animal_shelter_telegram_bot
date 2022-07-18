package pro.sky.animal_shelter_telegram_bot.service;

import com.pengrad.telegrambot.model.File;
import com.pengrad.telegrambot.model.Update;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.animal_shelter_telegram_bot.model.pets.PhotoOfPet;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface PhotoOfPetService {

    void uploadPhotoOfPet(Long reportId, MultipartFile avatarFile) throws IOException;

    void downloadPhotoOfPet(Long reportId, HttpServletResponse response) throws IOException;

    PhotoOfPet findPhotoByReportId(Long reportId);

    void uploadPhotoFromTg(Long chatId, byte[] data, File file, String date) throws IOException;

}
