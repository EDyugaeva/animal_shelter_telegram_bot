package pro.sky.animal_shelter_telegram_bot.service.impl;

import com.pengrad.telegrambot.model.PhotoSize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.animal_shelter_telegram_bot.model.PetOwner;
import pro.sky.animal_shelter_telegram_bot.model.Report;
import pro.sky.animal_shelter_telegram_bot.model.pets.PhotoOfPet;
import pro.sky.animal_shelter_telegram_bot.repository.PetOwnerRepository;
import pro.sky.animal_shelter_telegram_bot.repository.ReportRepository;
import pro.sky.animal_shelter_telegram_bot.service.PhotoOfPetService;
import pro.sky.animal_shelter_telegram_bot.service.ReportService;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;

/**
 * Service for working with repository ReportRepository
 */
@Service
public class ReportServiceImpl implements ReportService {

    Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);

    private final ReportRepository reportRepository;

    private final PetOwnerRepository petOwnerRepository;

    public ReportServiceImpl(ReportRepository reportRepository, PetOwnerRepository petOwnerRepository) {
        this.reportRepository = reportRepository;
        this.petOwnerRepository = petOwnerRepository;
    }


    @Override
    public Report addReport(Report report) {
        Report addingReport = reportRepository.save(report);
        logger.info("Report {} is saved", addingReport);
        return addingReport;
    }

    @Override
    public void deleteReport(Report report) {
        reportRepository.deleteById(report.getId());
        logger.info("Report {} is deleted", report);
    }

    @Override
    public void deleteReport(Long id) {
        reportRepository.deleteById(id);
        logger.info("Report with id {} is deleted", id);

    }

    @Override
    public Report findReport(Long id) {
        Report findingReport = reportRepository.findById(id).get();
        logger.info("Report with id {} is found", id);
        return findingReport;
    }

    @Override
    public Report changeReport(Report report) {
        Report changingReport = reportRepository.save(report);
        logger.info("Report {} is saved", report);
        return changingReport;
    }

    /**
     * Saving report (or changing) by text from telegram
     *
     * @param text   - from mrssage
     * @param chatId - from update
     * @param date   - local date (now)
     * @return message to send
     */
    @Override
    public String setReportToDataBase(String text, Long chatId, String date) {
        logger.info("Setting report to database");
        String reportText = "Отчет с текстом: --" + text + "-- добавлен ";

        Report report = findReportByChatIdAndDate(chatId, date);

        String[] parsingText = text.split("-");

        if (parsingText.length == 3) {
            report.setHealth(parsingText[0]);
            report.setDiet(parsingText[1]);
            report.setChangeInBehavior(parsingText[2]);
            logger.info("Отчет без ошибок запарсен. Состоит из частей: {}, {}, {}", parsingText[0], parsingText[1], parsingText[2]);
        } else {
            logger.warn("Mistake in parsing");
            throw new IllegalArgumentException("Mistake in report");
        }

        reportRepository.save(report);
        logger.info("Отчет {} сохранен", report.getId());

        return reportText;
    }

    /**
     * @param chatId - from update
     * @param date   - local date (now)
     * @return report
     */
    public Report findReportByChatIdAndDate(Long chatId, String date) {
        logger.info("Start finding report by chat id and date");
        PetOwner petOwner = petOwnerRepository.findPetOwnerByChatId(chatId).get();
        Report report = reportRepository.findReportByPetOwnerAndDateOfReport(petOwner, date).orElse(new Report());
        if (report.getdateOfReport() == null || report.getPetOwner() == null) {
            logger.info("report is null");
            report.setdateOfReport(date);
            report.setPetOwner(petOwner);
        }
        return report;

    }

}
