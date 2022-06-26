package pro.sky.animal_shelter_telegram_bot.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface PhotoOfPetService {

    void uploadPhotoOfPet(Long reportId, MultipartFile avatarFile);

    void downloadPhotoOfPet(Long reportId, HttpServletResponse response);
}
