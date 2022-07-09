package pro.sky.animal_shelter_telegram_bot.service;

import org.springframework.web.multipart.MultipartFile;
import pro.sky.animal_shelter_telegram_bot.model.pets.PhotoOfPet;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

public interface PhotoOfPetService {

    void uploadPhotoOfPet(Long reportId, MultipartFile avatarFile) throws IOException;

    void downloadPhotoOfPet(Long reportId, HttpServletResponse response) throws IOException;

    PhotoOfPet findPhotoByReportId(Long reportId);

    void savePhotoFromStringURL(String urlString, Long chatId, String date);

    }
