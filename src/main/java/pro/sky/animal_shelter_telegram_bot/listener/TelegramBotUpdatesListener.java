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
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    /**
     * filter keyboard variants to get response after "/start";
     * cases "Прислать отчет о питомце" and "Позови волонтера" mast be made later
     *
     * @param update
     */
    private void scanUpdates(Update update) {
        switch (update.message().text()) {
            case "Узнать информацию о приюте":
                logger.info("update for message: Узнать информацию о приюте");
                consultationWithNewUser(update);
            case "Как взять собаку из приюта":
                logger.info("update for message: Как взять собаку из приюта");
                consultationWithPotentialDogOwner(update);
            case "Прислать отчет о питомце":
                logger.info("update for message: Прислать отчет о питомце");
            case "Позови волонтера":
                logger.info("update for message: Позови волонтера");

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
                    new String[]{"Узнать информацию о приюте", "Как взять собаку из приюта"},
                    new String[]{"Прислать отчет о питомце", "Позвать волонтера"})
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
                new String[]{"О приюте", "Расписание работы приюта, адрес и схема проезда"},
                new String[]{"Общие рекомендации о ТБ на территории приюта", "Принять и записать контактные данные для связи"},
                new String[]{"Позвать волонтера"})
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
                new String[]{"Правила знакомства с собакой до того, как забрать ее из приюта", "Список документов, для того чтобы взять собаку из приюта"},
                new String[]{"Список рекомендаций по транспортировке животного", "Список рекомендаций по обустройству дома для щенка"},
                new String[]{"Список рекомендаций по обустройству дома для взрослой собаки", "Список рекомендаций по обустройству дома для взрослой собаки с ограниченными возмоностями"},
                new String[]{"Советы кинолога по первичному общению с собакой", "Список проверенных кинологов"},
                new String[]{"Список причин отказа в заборе собаки из приюта", "Принять и записать контактные данные для связи"},
                new String[]{"Позвать волонтера"})
                .oneTimeKeyboard(true)
                .resizeKeyboard(true)
                .selective(true);
        sendMessageWithKeyboard(update, message, keyboardMarkup);
    }

    /**
     *Send message to chat from update
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

}
