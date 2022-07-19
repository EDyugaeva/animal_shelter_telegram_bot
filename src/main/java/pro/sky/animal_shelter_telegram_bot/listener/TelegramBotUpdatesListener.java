package pro.sky.animal_shelter_telegram_bot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;


import com.pengrad.telegrambot.model.File;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.*;

import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;

import com.pengrad.telegrambot.response.GetFileResponse;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import pro.sky.animal_shelter_telegram_bot.model.Volunteer;
import pro.sky.animal_shelter_telegram_bot.service.PetOwnerService;
import pro.sky.animal_shelter_telegram_bot.service.PhotoOfPetService;
import pro.sky.animal_shelter_telegram_bot.service.ReportService;
import pro.sky.animal_shelter_telegram_bot.service.VolunteerService;

import javax.annotation.PostConstruct;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static pro.sky.animal_shelter_telegram_bot.listener.MessageAnswersConstance.*;
import static pro.sky.animal_shelter_telegram_bot.listener.MessageConstance.*;


@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private boolean savingReport = false;

    private boolean shelterForCats = false;

    private final PetOwnerService petOwnerService;

    private final PhotoOfPetService photoOfPetService;

    private final ReportService reportService;

    private final VolunteerService volunteerService;

    private final TelegramBot telegramBot;

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    public TelegramBotUpdatesListener(PetOwnerService petOwnerService, PhotoOfPetService photoOfPetService, ReportService reportService, VolunteerService volunteerService, TelegramBot telegramBot) {
        this.petOwnerService = petOwnerService;
        this.photoOfPetService = photoOfPetService;
        this.reportService = reportService;
        this.volunteerService = volunteerService;
        this.telegramBot = telegramBot;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    /**
     * receive incoming updates
     * scan updates, then filter to key message
     */
    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            if (savingReport) {
                savingReports(update);
            }
            if (update.message() == null) {
                logger.info("Empty message");
            } else if
            (update.message().text() != null) {
                logger.info(update.message().text());
                sendStartMessage(update);
                scanUpdates(update);
                makeReplies(update);
            } else if (update.message().contact() != null) {
                petOwnerService.setPetOwnersName(update.message().contact().firstName(), update.message().chat().id());
                petOwnerService.setPetOwnersPhoneNumber(update.message().contact().phoneNumber(), update.message().chat().id());
                sendMessage(update, SAVED_PHONE_NUMBER);
                sendMenu(update);
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    /**
     * Send standard text replies
     * @param update - from process
     */
    private void makeReplies(Update update) {
        String message = "";
        switch (update.message().text()) {
            case BUTTON_INFO_SHELTER:
                message = INFO_SHELTER;
                break;
            case BUTTON_GENERAL_RECOMMENDATION:
                message = GENERAL_RECOMMENDATION;
                break;
            case BUTTON_RULES_BEFORE_ADOPTING:
                message = RULES_BEFORE_ADOPTING;
                break;
            case BUTTON_LIST_DOCUMENTS_TO_ADOPT:
                message = LIST_DOCUMENTS_TO_ADOPT;
                break;
            case BUTTON_RECOMMENDATION_ABOUT_TRANSPORTATION:
                message = RECOMMENDATION_ABOUT_TRANSPORTATION;
                break;
            case BUTTON_RECOMMENDATION_ABOUT_HOME_FOR_PUPPY:
                message = RECOMMENDATION_ABOUT_HOME_FOR_PUPPY;
                break;
            case BUTTON_RECOMMENDATION_ABOUT_HOME_FOR_ADULT_DOG:
                message = RECOMMENDATION_ABOUT_HOME_FOR_ADULT_DOG;
                break;
            case BUTTON_RECOMMENDATION_ABOUT_HOME_FOR_DOG_WITH_LIMITED_OPPORTUNITIES:
                message = RECOMMENDATION_ABOUT_HOME_FOR_DOG_WITH_LIMITED_OPPORTUNITIES;
                break;
            case BUTTON_ADVICE_CYNOLOGIST:
                message = ADVICE_CYNOLOGIST;
                break;
            case BUTTON_LIST_OF_CYNOLOGISTS:
                message = LIST_OF_CYNOLOGISTS;
                break;
            case BUTTON_LIST_OF_REASONS_OF_REFUSIAL:
                message = LIST_OF_REASONS_OF_REFUSIAL;
                break;

        }
        sendMessage(update, message, KEYBOARD_BACK);
    }

    /**
     * filter keyboard variants to get response on buttons, with need special logic;
     *
     * @param update - from process
     */
    private void scanUpdates(Update update) {
        switch (update.message().text()) {
            case BUTTON1_1:
                logger.info("update for message: " + BUTTON1_1);
                consultationWithNewUser(update);
                break;
            case BUTTON1_2:
                logger.info("update for message: " + BUTTON1_2);
                consultationWithPotentialDogOwner(update);
                break;
            case BUTTON1_3:
                logger.info("update for message: " + BUTTON1_3);
                sendMessage(update, "Отправьте отчет в виде: состояние здоровья питомца-диета-изменение в поведении ");
                savingReport = true;
                logger.info("saving reports = true");
                break;
            case BUTTON_ASKING_VOLUNTEER:
                logger.info("update for message: " + BUTTON_ASKING_VOLUNTEER);
                askVolunteer(update);
                break;
            case BUTTON_SCHEDULE:
                sendAddressAndSchedule(update);
                break;
            case BUTTON_SAVING_CONTACTS:
                savingPhoneNumber(update);
                break;
            case BUTTON_BACK:
                sendMenu(update);
                break;

        }
    }

    /**
     * Send message with keyboard to save message
     * @param update - from process
     */
    private void savingPhoneNumber(Update update) {
        logger.info("Start saving number and name");
        Keyboard keyboard = new ReplyKeyboardMarkup(
                new KeyboardButton("contact").requestContact(true),
                new KeyboardButton(BUTTON_BACK))
                .oneTimeKeyboard(true)
                .resizeKeyboard(true)
                .selective(true);
        sendMessage(update, "Отправьте свой контакт нам", keyboard);
    }

    /**
     * Is calling volunteer
     * If there are none volunteer - send message, that they are busy
     * @param update - from process
     */
    private void askVolunteer(Update update) {
        Long chatId = update.message().chat().id();
        if (!petOwnerService.petOwnerHasPhoneNumber(chatId)) {
            sendMessage(update, "Для начала необходимо сохранить Ваши контакты. Затем нажмите кнопку --Позвать волонтера-- повторно");
            savingPhoneNumber(update);

        }
        if (petOwnerService.petOwnerHasPhoneNumber(chatId)) {
            String message = "Вас просил связаться " + petOwnerService.findPetOwnerByChatId(chatId).getFirstName() +
                    " по номеру телефона " + petOwnerService.findPetOwnerByChatId(chatId).getPhoneNumber();
            try {
                sendMessageToVolunteer(message);
                sendMessage(update, "Ваши контакты отправлены волонтеру. Он с вами свяжется", KEYBOARD_BACK);
            } catch (NotFoundException e) {
                sendMessage(update, "в данный момент нет свободных волонтеров. Пожалуйста, обратитесь позже");

            }
        }
    }

    /**
     *  Send all volunteers a message
     * @param message - message from askVolunteer
     */
    private void sendMessageToVolunteer(String message) {
        List<Volunteer> volunteers = volunteerService.findAllVolunteers();
        for (Volunteer volunteer : volunteers) {
            Long chatId = volunteer.getChatId();
            SendMessage sendMessage = new SendMessage(chatId, message);
            SendResponse response = telegramBot.execute(sendMessage);
            if (response.isOk()) {
                logger.info("message: {} is sent ", message);
            } else {
                logger.warn("Message was not sent. Error code:  " + response.errorCode());
            }
        }

    }


    /**
     * Saving reports to database. There is 2 points: saving text and saving photo
     *
     * @param update - from process
     */
    private void savingReports(Update update) {
        long chatId = update.message().chat().id();
        String localDate = LocalDate.now().toString();

        if (update.message().text() == null) {
            logger.info("Report is saving (photo)");

            GetFile getFileRequest = new GetFile(update.message().photo()[2].fileId());
            GetFileResponse getFileResponse = telegramBot.execute(getFileRequest);

            try {
                File file = getFileResponse.file();
                byte[] fileContent = telegramBot.getFileContent(file);
                photoOfPetService.uploadPhotoFromTg(chatId, fileContent, file, localDate);

            } catch (IOException e) {
                logger.error("Photo was not downloaded");
            }
            sendMessage(update, "Фотография успешно сохранена. Спасибо! Ждем нового отчета завтра");
            savingReport = false;


        } else if (!update.message().text().equals(BUTTON1_3)) {
            logger.info("Report is saving (text)");
            try {
                String[] message = reportService.setReportToDataBase(update.message().text(), chatId, localDate);
                sendMessage(update, "Состояние здоровья: " + message[0] + " сохранено");
                sendMessage(update, "Диета питомца: " + message[1] + " сохранено");
                sendMessage(update, "Изменение в поведении: " + message[2] + " сохранено");
                sendMessage(update, "А теперь отправьте фотографию своего питомца", KEYBOARD_BACK);
            } catch (IllegalArgumentException e) {
                sendMessage(update, "Отчет заполнен с ошибкой");
            }
            catch (InvalidDataAccessApiUsageException e) {
                savingReport = false;
                sendMessage(update, "У вас нет питомца. Обратитесь в приют");
                sendMenu(update);
            }
            sendMessage(update, "А теперь отправьте фотографию своего питомца", KEYBOARD_BACK);
        }
    }

    /**
     * Response on command /start or back
     *
     * @param update from process
     */
    private void sendStartMessage(Update update) {
        if (update.message().text().equals("/start") || update.message().text().equals(BUTTON_CHOSE_SHELTER)) {
            String message = "Выберете какой приют Вас интересует";
            ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(
                    new String[]{"Приют для кошек"},
                    new String[]{"Приют для собак"})
                    .oneTimeKeyboard(true)
                    .resizeKeyboard(true)
                    .selective(true);
            sendMessage(update, message, keyboardMarkup);
        }
        if (update.message().text().equals("Приют для кошек")) {
            shelterForCats = true;
            sendMenu(update);
        }
        if (update.message().text().equals("Приют для собак")) {
            shelterForCats = false;
            sendMenu(update);
        }

    }

    /**
     * Keyboard on command /start or back to menu. It makes keyboard with 4 variants
     *
     * @param update from process
     */
    private void sendMenu(Update update) {
        String message = "Приветствуем! Это телеграм бот приюта для животных";
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(
                new String[]{BUTTON1_1, BUTTON1_2},
                new String[]{BUTTON1_3, BUTTON_ASKING_VOLUNTEER},
                new String[]{BUTTON_CHOSE_SHELTER}
        )
                .oneTimeKeyboard(true)
                .resizeKeyboard(true)
                .selective(true);
        sendMessage(update, message, keyboardMarkup);
    }


    /**
     * Response to the message "Узнать информацию о приюте". It says Hello to user and gives keyboard with 5 variants
     *
     * @param update from scanUpdates
     */
    private void consultationWithNewUser(Update update) {
        String message = "Привет, " + update.message().chat().firstName() + "! Здесь некотороя информация о работе нашего приюта. Что Вам интересно?";
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(
                new String[]{BUTTON_INFO_SHELTER, BUTTON_SCHEDULE},
                new String[]{BUTTON_GENERAL_RECOMMENDATION, BUTTON_SAVING_CONTACTS},
                new String[]{BUTTON_ASKING_VOLUNTEER, BUTTON_BACK})
                .oneTimeKeyboard(true)
                .resizeKeyboard(true)
                .selective(true);
        sendMessage(update, message, keyboardMarkup);
    }

    /**
     * Response to the message "Как взять собаку из приюта". It says accompanying message to user and gives keyboard with 11 variants
     *
     * @param update from scanUpdates
     */
    private void consultationWithPotentialDogOwner(Update update) {
        String message = "Мы поможем разобраться с бюрократическими и бытовыми вопросами.)";

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(
                new String[]{BUTTON_RULES_BEFORE_ADOPTING, BUTTON_LIST_DOCUMENTS_TO_ADOPT},
                new String[]{BUTTON_RECOMMENDATION_ABOUT_TRANSPORTATION, BUTTON_RECOMMENDATION_ABOUT_HOME_FOR_PUPPY},
                new String[]{BUTTON_RECOMMENDATION_ABOUT_HOME_FOR_ADULT_DOG, BUTTON_RECOMMENDATION_ABOUT_HOME_FOR_DOG_WITH_LIMITED_OPPORTUNITIES})
                .oneTimeKeyboard(true)
                .resizeKeyboard(true)
                .selective(true);

        if (!shelterForCats) {
            keyboardMarkup.addRow(BUTTON_ADVICE_CYNOLOGIST, BUTTON_LIST_OF_CYNOLOGISTS);

        }
        keyboardMarkup.addRow(BUTTON_LIST_OF_REASONS_OF_REFUSIAL, BUTTON_SAVING_CONTACTS);
        keyboardMarkup.addRow(BUTTON_ASKING_VOLUNTEER, BUTTON_BACK);


        sendMessage(update, message, keyboardMarkup);
    }

    /**
     * Send message to chat from update
     *
     * @param update  - from process
     * @param message - text which will be sent
     */
    private void sendMessage(Update update, String message) {
        Long chatId = update.message().chat().id();
        SendResponse response = telegramBot.execute(new SendMessage(chatId, message));
        if (response.isOk()) {
            logger.info("message: {} is sent ", message);
        } else {
            logger.warn("Message was not sent.  " + response.description());
        }
    }

    /**
     * Send message with keyboard to chat from update
     *
     * @param update         - from process
     * @param message        - text, which will be sent
     * @param keyboardMarkup - keyboard, which could be sent with message
     */
    private void sendMessage(Update update, String message, Keyboard keyboardMarkup) {
        Long chatId = update.message().chat().id();
        SendMessage sendMessage = new SendMessage(chatId, message).replyMarkup(keyboardMarkup);
        SendResponse response = telegramBot.execute(sendMessage);
        if (response.isOk()) {
            logger.info("message: " + message + " is sent");
        }
    }

    /**
     * Send message to message "Расписание работы приюта, адрес и схема проезда"
     * Photo is on URL, should be another
     *
     * @param update - from process
     */
    private void sendAddressAndSchedule(Update update) {
        Long chatId = update.message().chat().id();
        sendMessage(update, SCHEDULE);
        sendMessage(update, ADDRESS);
        SendResponse response = telegramBot.execute(new SendPhoto(chatId, "https://www.imgonline.com.ua/examples/bee-on-daisy.jpg"));
        if (response.isOk()) {
            logger.info("photo is sent ");
        } else {
            logger.warn("Photo was not sent. Error code:  " + response.errorCode());
        }

    }

}
