package pro.sky.animal_shelter_telegram_bot.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.sky.animal_shelter_telegram_bot.model.Report;
import pro.sky.animal_shelter_telegram_bot.model.Volunteer;
import pro.sky.animal_shelter_telegram_bot.service.ReportService;
import pro.sky.animal_shelter_telegram_bot.service.VolunteerService;

/**
 * Service for working with repository ReportRepository
 */
public class ReportServiceImpl implements ReportService {

    Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);

    private final ReportService reportService;

    public ReportServiceImpl(ReportService reportService) {
        this.reportService = reportService;
    }


    @Override
    public Report addReport(Report report) {
        Report addingReport = ReportRepository.save(report);
        logger.info("Report {} is saved", addingReport);
        return addingReport;
    }

    @Override
    public void deleteReport(Report report) {
        ReportRepository.deleteById(report.getId());
        logger.info("Report {} is deleted", report);
    }

    @Override
    public void deleteReport(Long id) {
        ReportRepository.deleteById(id);
        logger.info("Report with id {} is deleted", id);

    }

    @Override
    public Report findReport(Long id) {
        Report findingReport = ReportRepository.findById(id).get();
        logger.info("Report with id {} is found", id);
        return findingReport;
    }

    @Override
    public Report changeReport(Report report) {
        Report changingReport = ReportRepository.save(report);
        logger.info("Report {} is saved", report);
        return changingReport;
    }

}
