package pro.sky.animal_shelter_telegram_bot.listener;

import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;

public class MessageConstance {

    public static final String BUTTON1_1 = "Узнать информацию о приюте";
    public static final String BUTTON1_2 = "Как взять питомца из приюта";
    public static final String BUTTON1_3 = "Прислать отчет о питомце";
    public static final String BUTTON_ASKING_VOLUNTEER = "Позвать волонтера";
    public static final String BUTTON_INFO_SHELTER = "О приюте";

    public static final String BUTTON_SCHEDULE = "Расписание работы приюта, адрес и схема проезда";

    public static final String BUTTON_GENERAL_RECOMMENDATION = "Общие рекомендации о ТБ на территории приюта";

    public static final String BUTTON_SAVING_CONTACTS = "Принять и записать контактные данные для связи";

    public static final String BUTTON_RULES_BEFORE_ADOPTING = "Правила знакомства с собакой до того, как забрать ее из приюта";

    public static final String BUTTON_LIST_DOCUMENTS_TO_ADOPT = "Список документов, для того чтобы взять собаку из приюта";

    public static final String BUTTON_RECOMMENDATION_ABOUT_TRANSPORTATION = "Список рекомендаций по транспортировке животного";

    public static final String BUTTON_RECOMMENDATION_ABOUT_HOME_FOR_PUPPY = "Список рекомендаций по обустройству дома для щенка или котенка";

    public static final String BUTTON_RECOMMENDATION_ABOUT_HOME_FOR_ADULT_DOG = "Список рекомендаций по обустройству дома для взрослого животного";

    public static final String BUTTON_RECOMMENDATION_ABOUT_HOME_FOR_DOG_WITH_LIMITED_OPPORTUNITIES = "Список рекомендаций по обустройству дома для животного с ограниченными возможностями";

    public static final String BUTTON_ADVICE_CYNOLOGIST = "Советы кинолога по первичному общению с собакой";

    public static final String BUTTON_LIST_OF_CYNOLOGISTS = "Список проверенных кинологов";

    public static final String BUTTON_LIST_OF_REASONS_OF_REFUSIAL = "Список причин отказа в заборе питомца из приюта";

    public static final String BUTTON_BACK = "Назад в основное меню";

    public static final String BUTTON_CHOSE_SHELTER = "К выбору приюта";

    public static final ReplyKeyboardMarkup KEYBOARD_BACK = new ReplyKeyboardMarkup(
            BUTTON_BACK)
            .oneTimeKeyboard(true)
            .resizeKeyboard(true)
            .selective(true);






}
