package pro.sky.animal_shelter_telegram_bot.service.impl;

import com.pengrad.telegrambot.model.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.animal_shelter_telegram_bot.model.Report;
import pro.sky.animal_shelter_telegram_bot.model.pets.PhotoOfPet;
import pro.sky.animal_shelter_telegram_bot.repository.PhotoOfPetRepository;
import pro.sky.animal_shelter_telegram_bot.service.PhotoOfPetService;
import pro.sky.animal_shelter_telegram_bot.service.ReportService;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

/**
 * Service for working with repository PhotoOfPetRepository
 */
@Service
public class PhotoOfPetServiceImpl implements PhotoOfPetService {
    @Lazy
    private final ReportService reportService;

    private final PhotoOfPetRepository photoOfPetRepository;

    Logger logger = LoggerFactory.getLogger(PhotoOfPetServiceImpl.class);
    @Value("covers")
    private String photoDir;

    public PhotoOfPetServiceImpl(ReportService reportService, PhotoOfPetRepository photoOfPetRepository) {
        this.reportService = reportService;
        this.photoOfPetRepository = photoOfPetRepository;
    }

    /**
     * Save photo to database
     *
     * @param reportId - report ID
     * @param photoFile - from telegram
     * @throws IOException - if exception in creating directories
     */
    @Override
    public void uploadPhotoOfPet(Long reportId, MultipartFile photoFile) throws IOException {
        int filesize = 1024;

        Report report = reportService.findReport(reportId);
        if (report == null) {
            logger.error("Not found report with ID " + reportId);
            throw new NullPointerException("Report with this ID does not exist: " + reportId);
        }
        Path filePath = Path.of(photoDir, report.getId() + "." + getExtensions(photoFile.getOriginalFilename()));
        try {
            Files.createDirectories(filePath.getParent());
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            logger.warn("File is not created");
            throw new IOException("Error in creating file");
        }

        try (
                InputStream is = photoFile.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, filesize);
                BufferedOutputStream bos = new BufferedOutputStream(os, filesize)
        ) {
            bis.transferTo(bos);
        } catch (IOException e) {
            logger.warn("Photo to report {} is not downloaded", reportId);
            throw new IOException("Download error");
        }
        PhotoOfPet photoOfPet = findPhotoByReportId(reportId);
        photoOfPet.setReport(report);
        photoOfPet.setFilePath(filePath.toString());
        photoOfPet.setFileSize(photoFile.getSize());
        try {
            photoOfPet.setData(photoFile.getBytes());
        } catch (IOException e) {
            logger.warn("Photo to report {} is not downloaded to database", reportId);
            throw new IOException("Download error");
        }
        photoOfPetRepository.save(photoOfPet);
        logger.debug("Photo of pet for report {} is saved ", reportId);

    }

    private String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    @Override
    public void downloadPhotoOfPet(Long reportId, HttpServletResponse response) throws IOException {
        int filesize = 1024;

        logger.debug("Downloading photoOfPet for report {}", reportId);
        PhotoOfPet photoOfPet = findPhotoByReportId(reportId);
        if (photoOfPet.getData() == null) {
            throw new NullPointerException("Photo from report with this ID {} does not exist: " + reportId);
        }
        Path path = Path.of(photoOfPet.getFilePath());
        try (
                InputStream is = Files.newInputStream(path);
                OutputStream os = response.getOutputStream();
                BufferedInputStream bis = new BufferedInputStream(is, filesize);
                BufferedOutputStream bos = new BufferedOutputStream(os, filesize)
        ) {
            response.setStatus(200);
            response.setContentLength((int) photoOfPet.getFileSize());
            bis.transferTo(bos);
        } catch (IOException e) {
            throw new IOException("Upload error");
        }
        logger.debug("Photo for report {} is downloaded", reportId);
    }

    @Override
    public PhotoOfPet findPhotoByReportId(Long reportId) {
        return photoOfPetRepository.findPhotoOfPetByReportId(reportId).orElse(new PhotoOfPet());
    }

    /**
     *  Saving (or changing) photo to database (without setting other params)
     * @param chatId  - from update
     * @param data - bytes[] from message
     * @param file - from message
     * @param date - date now
     * @throws IOException - if file was not created
     */
    @Override
    public void uploadPhotoFromTg(Long chatId, byte[] data, File file, String date) throws IOException {
        Report report = reportService.findReportByChatIdAndDate(chatId, date);
        PhotoOfPet photoOfPet = findPhotoByReportId(report.getId());
        Path filePath = Path.of(String.valueOf(data), "capture" + "." + getExtensions(Objects.requireNonNull(file.filePath())));
        try {
            Files.createDirectories(filePath.getParent());
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            logger.warn("File is not created");
            throw new IOException("Error in creating file");
        }
        photoOfPet.setReport(report);
        photoOfPet.setFilePath(filePath.toString());
        photoOfPet.setFileSize(file.fileSize());
        photoOfPet.setData(data);

        photoOfPetRepository.save(photoOfPet);


    }
}
