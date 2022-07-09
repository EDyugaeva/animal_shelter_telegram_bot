package pro.sky.animal_shelter_telegram_bot.service;

import pro.sky.animal_shelter_telegram_bot.model.Report;

public interface ReportService {
    Report addReport(Report report);

    void deleteReport(Report report);

    void deleteReport(Long id);

    Report findReport(Long id);

    Report changeReport(Report report);

    String setReportToDataBase(String text, Long chatId, String date);

    Report findReportByChatIdAndDate(Long chatId, String date);

}
