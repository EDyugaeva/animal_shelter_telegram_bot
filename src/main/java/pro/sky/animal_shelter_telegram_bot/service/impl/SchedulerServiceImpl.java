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
import pro.sky.animal_shelter_telegram_bot.service.PetOwnerService;
import pro.sky.animal_shelter_telegram_bot.service.ReportService;
import pro.sky.animal_shelter_telegram_bot.service.SchedulerService;
import pro.sky.animal_shelter_telegram_bot.service.VolunteerService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Service for working with repository SchedulerRepository
 */

@Service
public class SchedulerServiceImpl implements SchedulerService {

    private final PetOwnerService petOwnerService;
    private final TelegramBot telegramBot;
    private final VolunteerService volunteerService;
    private final ReportService reportService;

    private final Logger logger = LoggerFactory.getLogger(SchedulerServiceImpl.class);

    public SchedulerServiceImpl(PetOwnerService petOwnerServiceImpl, TelegramBot telegramBot, VolunteerService volunteerServiceImpl, ReportService reportServiceImpl) {
        this.petOwnerService = petOwnerServiceImpl;
        this.telegramBot = telegramBot;
        this.volunteerService = volunteerServiceImpl;
        this.reportService = reportServiceImpl;
    }

    @Scheduled(cron = "0 0/1 * * * *")
    public void endOfProbationChecking() {

        Volunteer volunteer = volunteerService.findVolunteer(1L);
        Collection<PetOwner> petOwnersList;
        try {
            petOwnersList = petOwnerService.getPetOwnerWithZeroDayOfProbation();
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
        try {
            Collection<PetOwner> petOwnersList = petOwnerService.getPetOwnerByDayOfProbation();
            for (PetOwner petOwner : petOwnersList) {
                logger.info("Processing scheduler task dailyReportReminder for {}", petOwner);
                SendMessage dailyReportReminderMsg = new SendMessage(petOwner.getChatId(),
                        "Здравствуйте, " + petOwner.getFirstName() + " " + petOwner.getLastName() +
                                "! Пришлите, пожалуйста, ежедневный отчёт о состоянии Вашего питомца.");
                telegramBot.execute(dailyReportReminderMsg);
                petOwner.setDayOfProbation(petOwner.getDayOfProbation() - 1);
                petOwnerService.changePetOwner(petOwner);
            }

        } catch (NotFoundException e) {
            logger.info("No pet owners with days of probation more then zero");

        }
    }

    @Scheduled(cron = "0 0/2 * * * *")
    public void volunteerCheckReportReminder() {
        List<Volunteer> volunteerList = volunteerService.findAllVolunteers();
        if (!volunteerList.isEmpty()) {
            try {
                Collection<Report> reportsList = new ArrayList<>(reportService.getUncheckedReports());
                for (Volunteer volunteer : volunteerList) {
                    logger.info("Processing scheduler task volunteerCheckReportReminder for {}", volunteer);
                    for (Report report : reportsList) {
                        SendMessage volunteerCheckReportReminderMsg = new SendMessage(volunteer.getChatId(),
                                "Привет, " + volunteer.getFirstName() + " " + volunteer.getLastName() +
                                        "! Тебе необходимо проверить отчёт с ID=" + report.getId().toString());
                        telegramBot.execute(volunteerCheckReportReminderMsg);
                    }
                }
            } catch (NullPointerException e) {
                logger.info("All reports are checked");
            }
        } else {
            logger.error("We do not have volunteers");
        }
    }

}