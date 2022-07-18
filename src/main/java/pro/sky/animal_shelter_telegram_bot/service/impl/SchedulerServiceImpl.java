package pro.sky.animal_shelter_telegram_bot.service.impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import pro.sky.animal_shelter_telegram_bot.model.PetOwner;
import pro.sky.animal_shelter_telegram_bot.model.Report;
import pro.sky.animal_shelter_telegram_bot.model.Volunteer;
import pro.sky.animal_shelter_telegram_bot.service.SchedulerService;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Service for working with repository SchedulerRepository
 */

@Service
public class SchedulerServiceImpl implements SchedulerService {

    private final PetOwnerServiceImpl petOwnerServiceImpl;
    private final TelegramBot telegramBot;
    private final VolunteerServiceImpl volunteerServiceImpl;
    private final ReportServiceImpl reportServiceImpl;

    private final Logger logger = LoggerFactory.getLogger(SchedulerServiceImpl.class);

    public SchedulerServiceImpl(PetOwnerServiceImpl petOwnerServiceImpl, TelegramBot telegramBot, VolunteerServiceImpl volunteerServiceImpl, ReportServiceImpl reportServiceImpl) {
        this.petOwnerServiceImpl = petOwnerServiceImpl;
        this.telegramBot = telegramBot;
        this.volunteerServiceImpl = volunteerServiceImpl;
        this.reportServiceImpl = reportServiceImpl;
    }

    @Scheduled(cron = "0 0/1 * * * *")
    public void endOfProbationChecking() {

        Volunteer volunteer = volunteerServiceImpl.findVolunteer(1L);
        Collection<PetOwner> petOwnersList;
        try {
            petOwnersList = petOwnerServiceImpl.getPetOwnerWithZeroDayOfProbation();
        } catch (NotFoundException e) {
            return;
        }

        if (petOwnersList.isEmpty()) {
            logger.info("No pet owners with days of probation equal zero");
        } else {
            for (PetOwner petOwner : petOwnersList) {
                logger.info("Processing scheduler task endOfProbationChecking for {}", petOwner);
                SendMessage endOfProbationCheckingMsg = new SendMessage(volunteer.getChatId(),
                        "Привет, " + volunteer.getFirstName() + " " + volunteer.getLastName() +
                                "! Похоже, что у усыновителя с ID=" + petOwner.getId() + " закончился испытательный период. Тебе необходимо решить, что делать дальше.");
                telegramBot.execute(endOfProbationCheckingMsg);
            }
        }
    }

    @Scheduled(cron = "5 0/1 * * * *")
    public void dailyReportReminder() {

        Collection<PetOwner> petOwnersList = petOwnerServiceImpl.getPetOwnerByDayOfProbation();

        if (petOwnersList.isEmpty()) {
            logger.info("No pet owners with days of probation more then zero");
        } else {
            for (PetOwner petOwner : petOwnersList) {
                logger.info("Processing scheduler task dailyReportReminder for {}", petOwner);
                SendMessage dailyReportReminderMsg = new SendMessage(petOwner.getChatId(),
                        "Здравствуйте, " + petOwner.getFirstName() + " " + petOwner.getLastName() +
                                "! Пришлите, пожалуйста, ежедневный отчёт о состоянии Вашего питомца.");
                telegramBot.execute(dailyReportReminderMsg);
                petOwner.setDayOfProbation(petOwner.getDayOfProbation() - 1);
                petOwnerServiceImpl.changePetOwner(petOwner);
            }
        }
    }

    @Scheduled(cron = "0 0/2 * * * *")
    public void volunteerCheckReportReminder() {

        Volunteer volunteer = volunteerServiceImpl.findVolunteer(1L);
        Collection<Report> reportsList = new ArrayList<>(reportServiceImpl.getUncheckedReports());

        if (reportsList.isEmpty()) {
            logger.info("No reports to check.");
        } else {
            logger.info("Processing scheduler task volunteerCheckReportReminder for {}", volunteer);
            for (Report report : reportsList) {
                SendMessage volunteerCheckReportReminderMsg = new SendMessage(volunteer.getChatId(),
                        "Привет, " + volunteer.getFirstName() + " " + volunteer.getLastName() +
                                "! Тебе необходимо проверить отчёт с ID=" + report.getId().toString());
                telegramBot.execute(volunteerCheckReportReminderMsg);
            }
        }
    }

}