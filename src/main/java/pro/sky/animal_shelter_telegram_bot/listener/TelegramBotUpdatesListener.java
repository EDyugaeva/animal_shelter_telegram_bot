package pro.sky.animal_shelter_telegram_bot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;


import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.*;

import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;

import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import java.util.List;

import static pro.sky.animal_shelter_telegram_bot.listener.MessageConstance.*;


@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final TelegramBot telegramBot;
    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);


    public TelegramBotUpdatesListener(TelegramBot telegramBot) {
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
            logger.info(update.message().text());
            sendStartMessage(update);
            scanUpdates(update);
            makereplies(update);

        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private void makereplies(Update update) {
        String message = "";
        switch (update.message().text()) {
            case BUTTON_INFO_SHELTER:
                message = "Информация о приюте";
                break;
            case BUTTON_GENERAL_RECOMMENDATION:
                message = "Общие рекомендации";
                break;
            case BUTTON_RULES_BEFORE_ADOPTING:
                message = "Правила знакомства";
                break;
            case BUTTON_LIST_DOCUMENTS_TO_ADOPT:
                message = "Лист документов необходимых для взятия животного";
                break;
            case BUTTON_LIST_RECOMMENDATION_ABOUT_TRANSPORTATION:
                message = "Рекомендации по транспортировке";
                break;
            case BUTTON_LIST_RECOMMENDATION_ABOUT_HOME_FOR_PUPPY:
                message = "Рекомендации по обустройству дома для щенят";
                break;
            case BUTTON_RECOMMENDATION_ABOUT_HOME_FOR_ADULT_DOG:
                message = "Рекомендации по обустройству дома для взрослых собак";
                break;
            case BUTTON_RECOMMENDATION_ABOUT_HOME_FOR_DOG_WITH_LIMITED_OPPORTUNITIES:
                message = "Рекомендации по обустройству дома для собак с ограниченными возможностями";
                break;
            case BUTTON_ADVICE_CYNOLOGIST:
                message = "Советы кинолога";
                break;
            case BUTTON_LIST_OF_CYNOLOGISTS:
                message = "Список кинологов";
                break;
            case BUTTON_LIST_OF_REASONS_OF_REFUSIAL:
                message = "Список причин для отказа";
                break;
        }

        sendMessage(update, message);


    }

    /**
     * filter keyboard variants to get response after "/start";
     * cases "Прислать отчет о питомце" and "Позови волонтера" mast be made later
     *
     * @param update
     */
    private void scanUpdates(Update update) {
        if (update.message().text() == null) {
            sendMessage(update, "пустое сообщение");
        }
        if (update.message().text().equals(BUTTON1_1)) {
            logger.info("update for message: " + BUTTON1_1);
            consultationWithNewUser(update);
        } else if (update.message().text().equals(BUTTON1_2)) {
            logger.info("update for message: " + BUTTON1_2);
            consultationWithPotentialDogOwner(update);
        } else if (update.message().text().equals(BUTTON1_3)) {
            logger.info("update for message: " + BUTTON1_3);

        } else if (update.message().text().equals(BUTTON_ASKING_VOLUNTEER)) {
            logger.info("update for message: " + BUTTON_ASKING_VOLUNTEER);
        }
        else if (update.message().text().equals("Расписание работы приюта, адрес и схема проезда")) {
            sendAdressAndScheldue(update);
        }


    }

    /**
     * Response on command /start. It makes keyboard with 4 variants
     *
     * @param update from process
     */
    private void sendStartMessage(Update update) {
        String message = "Приветствуем! Это телеграм бот приюта для собак";
        if (update.message().text() != null && update.message().text().equals("/start")) {
            logger.info("Start message to chatId" + update.message().chat().id());
            ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(
                    new String[]{BUTTON1_1, BUTTON1_2},
                    new String[]{BUTTON1_3, BUTTON_ASKING_VOLUNTEER})
                    .oneTimeKeyboard(true)
                    .resizeKeyboard(true)
                    .selective(true);
            sendMessageWithKeyboard(update, message, keyboardMarkup);
        }
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
                new String[]{BUTTON_ASKING_VOLUNTEER})
                .oneTimeKeyboard(true)
                .resizeKeyboard(true)
                .selective(true);
        sendMessageWithKeyboard(update, message, keyboardMarkup);
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
                new String[]{BUTTON_LIST_RECOMMENDATION_ABOUT_TRANSPORTATION, BUTTON_LIST_RECOMMENDATION_ABOUT_HOME_FOR_PUPPY},
                new String[]{BUTTON_RECOMMENDATION_ABOUT_HOME_FOR_ADULT_DOG,BUTTON_RECOMMENDATION_ABOUT_HOME_FOR_DOG_WITH_LIMITED_OPPORTUNITIES },
                new String[]{BUTTON_ADVICE_CYNOLOGIST, BUTTON_LIST_OF_CYNOLOGISTS},
                new String[]{BUTTON_LIST_OF_REASONS_OF_REFUSIAL, BUTTON_SAVING_CONTACTS},
                new String[]{BUTTON_ASKING_VOLUNTEER})
                .oneTimeKeyboard(true)
                .resizeKeyboard(true)
                .selective(true);
        sendMessageWithKeyboard(update, message, keyboardMarkup);
    }

    /**
     * Send message to chat from update
     *
     * @param update
     * @param message
     */
    private void sendMessage(Update update, String message) {
        Long chatId = update.message().chat().id();
        SendMessage sendMessage = new SendMessage(chatId, message);
        SendResponse response = telegramBot.execute(sendMessage);
        if (response.isOk()) {
            logger.info("message: {} is sent ", message);
        } else {
            logger.warn("Message was not sent. Error code:  " + response.errorCode());
        }
    }

    /**
     * Send message with keyboard to chat from update
     *
     * @param update
     * @param message
     * @param keyboardMarkup
     */
    private void sendMessageWithKeyboard(Update update, String message, ReplyKeyboardMarkup keyboardMarkup) {
        Long chatId = update.message().chat().id();
        SendResponse response = telegramBot.execute(new SendMessage(chatId, message).replyMarkup(keyboardMarkup));
        if (response.isOk()) {
            logger.info("message: {} is sent ", message);
        } else {
            logger.warn("Message was not sent. Error code:  " + response.errorCode());
        }
    }

//    private void saveContacts(Update update) {
//        if (update.message().text() != null && update.message().text().equals("Принять и записать контактные данные для связи")) {
//            SendResponse response;
//            response = telegramBot.execute((new SendMessage(update.message().chat().id(), "Принять и записать контактные данные для связи")).replyMarkup(new ForceReply(isSelective));
//
//            CallbackQuery callbackQuery = update.callbackQuery();
//
//        }
//    }

    /**
     * Send message to message "Расписание работы приюта, адрес и схема проезда"
     * Photo is on URL
     *
     * @param update
     */
    private void sendAdressAndScheldue(Update update) {
        Long chatId = update.message().chat().id();
        String scheldue = "Время работы: " + "\n" +
                "пн - 14-22" + "\n" +
                "вт - 10-22" + "\n" +
                "ср - 8-18" + "\n" +
                "чт - выходной " + "\n" +
                "пт - 10-22" + "\n" +
                "сб - 10-20" + "\n" +
                "вс - 12-22";

        sendMessage(update, scheldue);
        String address = "Какой-то адрес приюта";
        sendMessage(update, address);

        SendResponse response = telegramBot.execute(new SendPhoto(chatId, "https://www.imgonline.com.ua/examples/bee-on-daisy.jpg"));
        if (response.isOk()) {
            logger.info("photo: {} is sent ");
        } else {
            logger.warn("Photo was not sent. Error code:  " + response.errorCode());
        }

    }

}
