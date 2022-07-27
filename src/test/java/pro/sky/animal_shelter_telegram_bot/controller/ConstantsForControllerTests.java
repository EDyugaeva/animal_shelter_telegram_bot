package pro.sky.animal_shelter_telegram_bot.controller;

import pro.sky.animal_shelter_telegram_bot.model.PetOwner;
import pro.sky.animal_shelter_telegram_bot.model.pets.Pet;

public class ConstantsForControllerTests {

    public final static String URL = "http://localhost:";
    public final static String PORT = "8080";
    public final static String PET_URL = "pet";
    public final static String PET_OWNER_URL = "pet-owner";
    public final static String REPORT_URL = "report";
    public final static String MARK_REPORT_URL = "mark-report";
    public final static String VOLUNTEER_URL = "volunteer";
    public final static String ALL_URL = "all";
    public final static String ZERO_PROBATION_URL = "zero-probation";
    public final static String PROBATION_DAYS_URL = "probation-days";
    public final static String PROBATION_SUCCESSFULLY = "probation-successfully";

    public final static Pet PET = new Pet();
    public final static PetOwner PET_OWNER = new PetOwner();

    public final static long ID = 1L;
    public final static String FIRST_NAME = "Ivan";
    public final static String LAST_NAME = "Ivanov";
    public final static long CHAT_ID = 12345678;
    public final static String PHONE_NUMBER = "1234567890";
    public final static int DAY_OF_PROBATION = 25;
    public final static String NAME_OF_PET = "Barsik";
    public final static String HEALTH = "Health is nice";
    public final static String EXTRA_INFO = "Extra info about..";
    public final static String DATE_OF_REPORT = "12.12.2021";
    public final static String DIET = "Meat";
    public final static String RESULT = "Report is fine";
    public final static String CHANGE_IN_BEHAVIOR = "Change in behavior of pet";
    public final static int AMOUNT_OF_DAY = -2;
}
