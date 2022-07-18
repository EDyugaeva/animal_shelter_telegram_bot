package pro.sky.animal_shelter_telegram_bot.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import pro.sky.animal_shelter_telegram_bot.model.PetOwner;
import pro.sky.animal_shelter_telegram_bot.model.Report;
import pro.sky.animal_shelter_telegram_bot.repository.PetOwnerRepository;
import pro.sky.animal_shelter_telegram_bot.repository.ReportRepository;
import pro.sky.animal_shelter_telegram_bot.service.ReportService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


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
        logger.info("Report with id {} is saved", addingReport);
        return addingReport;
    }

    @Override
    public Report deleteReport(Long id) {
        if (reportRepository.findById(id).isEmpty()){
            logger.info("Report with id {} is not found", id);
            return null;
        }
        Report deleteReport = reportRepository.findById(id).get();
        reportRepository.deleteById(id);
        logger.info("Report with id {} is deleted", id);
        return deleteReport;
    }

    @Override
    public Report findReport(Long id) {
        if (reportRepository.findById(id).isEmpty()) {
            logger.info("Report with id {} is not found", id);
            return null;
        }
        Report report = reportRepository.findById(id).get();
        logger.info("Report with id {} is found", id);
        return report;
    }

    @Override
    public Report changeReport(Report report) {
        if (reportRepository.findById(report.getId()).isEmpty()) {
            logger.info("Report with id {} is not found", report.getId());
            return null;
        }
        Report changingReport = reportRepository.save(report);
        logger.info("Report {} is saved", report);
        return changingReport;
    }

    /**
     * Saving report (or changing) by text from telegram
     *
     * @param text   - from message
     * @param chatId - from update
     * @param date   - local date (now)
     * @return message to send
     */
    @Override
    public String[] setReportToDataBase(String text, Long chatId, String date) {
        logger.info("Setting report to database");

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

        return parsingText;
    }

    /**
     * @param chatId - from update
     * @param date   - local date (now)
     * @return report
     */
    public Report findReportByChatIdAndDate(Long chatId, String date) {
        logger.info("Start finding report by chat id and date");
        PetOwner petOwner = petOwnerRepository.findPetOwnerByChatId(chatId).orElse(new PetOwner());
        Report report = reportRepository.findReportByDateOfReportAndPetOwner_ChatId(date, chatId).orElse(new Report());
        if (report.getdateOfReport() == null || report.getPetOwner() == null) {
            logger.info("report is null");
            report.setdateOfReport(date);
            report.setPetOwner(petOwner);
        }
        return report;

    }

    @Override
    public Collection<Report> getUncheckedReports() {
        List<Report> reportsList = new ArrayList<>(reportRepository.getUncheckedReports());
        if (reportsList.isEmpty()) {
            throw new NullPointerException("List of result is empty");
        }
        logger.info("Get list of unchecked reports");
        return reportsList;
    }

    /**
     * Set mark to report (will be saved in database)
     * @param id - report id (will get from telegram chat)
     * @param result - mark
     * @return saved report
     */
    @Override
    public Report setMarkOnReport(Long id, String result) {
        Report report = findReport(id);
        if (report == null) {
            logger.warn("Report with ID {} was not found", id);
            throw new NotFoundException("Report was not found");
        }
        if (result.isEmpty()) {
            logger.warn("Result us empty");
            throw new NullPointerException("Result us empty");
        }
        report.setResult(result);
        report.setReportChecked(true);
        logger.info("Mark was set on report " + id);
        reportRepository.save(report);
        return report;
    }



}
