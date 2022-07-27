package pro.sky.animal_shelter_telegram_bot.service;

import pro.sky.animal_shelter_telegram_bot.model.Report;

import java.util.Collection;

public interface ReportService {
    Report addReport(Report report);

    Report deleteReport(Long id);

    Report findReport(Long id);

    Report changeReport(Report report);

    String[] setReportToDataBase(String text, Long chatId, String date);

    Report findReportByChatIdAndDate(Long chatId, String date);

    Collection<Report> getUncheckedReports();

    Report setMarkOnReport(Long id, String result);
}
