package pro.sky.animal_shelter_telegram_bot.controller;

import pro.sky.animal_shelter_telegram_bot.model.PetOwner;
import pro.sky.animal_shelter_telegram_bot.model.Report;
import pro.sky.animal_shelter_telegram_bot.model.pets.Pet;

import java.util.ArrayList;
import java.util.Collection;

public class ConstantsForControllerTests {

    public final static String URL = "http://localhost:";
    public final static String PORT = "8080";
    public final static String PET_URL = "pet";
    public final static String PET_OWNER_URL = "pet-owner";
    public final static String REPORT_URL = "report";
    public final static String VOLUNTEER_URL = "volunteer";
    public final static String ALL_URL = "all";


    public final static Pet PET = new Pet();
    public final static PetOwner PET_OWNER = new PetOwner();
    public final static Collection<Report> REPORTS = new ArrayList<>();

    public final static long ID = 1L;
    public final static String FIRST_NAME = "Ivan";
    public final static String LAST_NAME = "Ivanov";
    public final static long CHAT_ID = 12345678;
    public final static String PHONE_NUMBER = "1234567890";
    public final static int DAY_OF_PROBATION = 25;
    public final static String NAME_OF_PET = "Barsik";
    public final static String HEALTH = "Health is nice";
    public final static String EXTRA_INFO_OF_PET = "This is a dog";
    public final static String DATE_OF_REPORT = "12.12.2021";
    public final static String DIET = "Meat";
    public final static String RESULT = "Report is fine";

    public final static String HELLO_MESSAGE_MAIN_CONTROLLER = "Welcome to our pet shelter!";

    public final static String HELLO_MESSAGE_PET_CONTROLLER = "You can do it by information of pet:\n" +
            "1. add pet information\n" +
            "2. get pet information\n" +
            "2. update pet information\n" +
            "4. remove pet information";
    public final static String HELLO_MESSAGE_PET_OWNER = "You can do it by information of pet owner:\n" +
            "1. add information about the owner of the pet\n" +
            "2. get information about the owner of the pet\n" +
            "2. update information about the owner of the pet\n" +
            "4. remove information about the owner of the pet\n";
    public final static String HELLO_MESSAGE_REPORT_CONTROLLER = "You can do it by reports\n" +
            "1. add new report\n" +
            "2. find report\n" +
            "2. update report\n" +
            "4. remove report\n";
    public final static String HELLO_MESSAGE_VOLUNTEER_CONTROLLER = "You can do it by information of volunteer:\n" +
            "1. add information about the volunteer\n" +
            "2. get information about the volunteer\n" +
            "2. update information about the volunteer\n" +
            "4. remove information about rhe volunteer\n";
}
