package pro.sky.animal_shelter_telegram_bot.service;

import pro.sky.animal_shelter_telegram_bot.model.PetOwner;
import pro.sky.animal_shelter_telegram_bot.model.Report;

import java.util.ArrayList;
import java.util.Collection;

public class ConstantsForServicesTest {

    protected static final String PHONE_NUMBER = "+79992214290";
    protected static final Long ID = 456789L;
    protected static final Long ID_2 = 789456L;

    protected static final PetOwner PET_OWNER_1 = new PetOwner();

    protected static final PetOwner PET_OWNER_2 = new PetOwner();

    protected static final Collection<PetOwner> COLLECTION_PET_OWNERS_NOT_ZERO = new ArrayList<>();

    protected static final Collection<PetOwner> COLLECTION_PET_OWNERS_ZERO = new ArrayList<>();

    protected static final Long CHAT_ID = 12456L;

    protected static final String NAME = "Name";

    protected static final String RESULT = "Хороший";
    protected static final Report REPORT_1 = new Report();
    protected static final Report REPORT_2 = new Report();

    protected static final String DATE = "17.07.22";




}
