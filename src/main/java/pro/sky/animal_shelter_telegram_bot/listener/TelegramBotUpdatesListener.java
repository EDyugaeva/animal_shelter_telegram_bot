package pro.sky.animal_shelter_telegram_bot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import java.util.List;


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

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            logger.info(update.message().text());
            Long chatId = update.message().chat().id();
            if (update.message().text() != null && update.message().text().equals("/start")) {
                sendHelloMessage(chatId);
            }
            scanUpdates(update);
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private void scanUpdates(Update update) {
        switch (update.message().text()) {
            case "Узнать информацию о приюте":
                System.out.println("its working");
                stage1(update);
            case "Как взять собаку из приюта":
                System.out.println("Этап 2");
            case "Прислать отчет о питомце":
                System.out.println("Этап 3");
            case "Позови волонтера":
                System.out.println("Позвать волонтера");
            case "Расписание работы приюта, адрес и схема проезда":
                sendAdressAndScheldue(update);


        }
    }

    private void sendHelloMessage(Long chatId) {
        String message = "Hello! This is my first Telegram Bot!";
        sendMessage(chatId, message);
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(
                new String[]{"Узнать информацию о приюте", "Как взять собаку из приюта"},
                new String[]{"Прислать отчет о питомце", "Позвать волонтера"})
                .oneTimeKeyboard(true)   // optional
                .resizeKeyboard(true)    // optional
                .selective(true);        // optional
        SendResponse request = telegramBot.execute(new SendMessage(chatId, "Пробуем отправить кнопки").replyMarkup(keyboardMarkup));

    }

    private void stage1(Update update) {
        String message = "Hello! This is bot for dog shelter";
        Long chatId = update.message().chat().id();
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(
                new String[]{"О приюте", "Расписание работы приюта, адрес и схема проезда"},
                new String[]{"Общие рекомендации о ТБ на территории приюта", "Принять и записать контактные данные для связи"},
                new String[]{"Позвать волонтера"})
                .oneTimeKeyboard(true)   // optional
                .resizeKeyboard(true)    // optional
                .selective(true);        // optional
        SendResponse request = telegramBot.execute(new SendMessage(chatId, message).replyMarkup(keyboardMarkup));
    }

    private void sendAdressAndScheldue(Update update) {
        Long chatId = update.message().chat().id();
        String message = "Время работы: " + "/n" +
                "пн - 14-22" +
                "вт - 10-22";
        sendMessage(chatId, message);
    }


    private void sendMessage(Long chatId, String message) {
        SendMessage sendMessage = new SendMessage(chatId, message);
        SendResponse response = telegramBot.execute(sendMessage);
        if (response.isOk()) {
            logger.info("message: {} is sent ", message);
        } else {
            logger.warn("Message was not sent. Error code:  " + response.errorCode());
        }
    }

    private void sendMessageWithReply(Update update, String message, ReplyKeyboardMarkup keyboardMarkup) {
        Long chatId = update.message().chat().id();
        SendMessage sendMessage = new SendMessage(chatId, message);
        SendResponse response = telegramBot.execute(sendMessage);

        if (response.isOk()) {
            logger.info("message: {} is sent ", message);
        } else {
            logger.warn("Message was not sent. Error code:  " + response.errorCode());
        }
    }


}
