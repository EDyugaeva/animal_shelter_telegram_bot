package pro.sky.animal_shelter_telegram_bot.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.animal_shelter_telegram_bot.model.Report;
import pro.sky.animal_shelter_telegram_bot.repository.ReportRepository;
import pro.sky.animal_shelter_telegram_bot.service.ReportService;

/**
 * Service for working with repository ReportRepository
 */
@Service
public class ReportServiceImpl implements ReportService {

    Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);

    private final ReportRepository reportRepository;

    public ReportServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
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

}
