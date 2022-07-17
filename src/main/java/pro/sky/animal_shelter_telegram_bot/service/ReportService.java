package pro.sky.animal_shelter_telegram_bot.service;

import org.webjars.NotFoundException;
import pro.sky.animal_shelter_telegram_bot.model.PetOwner;
import pro.sky.animal_shelter_telegram_bot.model.Report;

import java.util.Collection;

import java.util.List;

public interface ReportService {
    Report addReport(Report report);

    void deleteReport(Report report);

    boolean deleteReport(Long id);

    Report findReport(Long id);

    Report changeReport(Report report);

    String[] setReportToDataBase(String text, Long chatId, String date);

    Report findReportByChatIdAndDate(Long chatId, String date);

    Report setMarkOnReport(Long id, String result);

    Collection<Report> getUncheckedReports();

}
