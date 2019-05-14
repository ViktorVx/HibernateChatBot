package org.pva.hibernateChatBot.telegramBot.constants;

public class ConstantStorage {

    public static final String MSG_EDIT_PERSON_LAST_NAME = "Введите фамилию:";
    public static final String MSG_EDIT_PERSON_FIRST_NAME = "Введите имя:";
    public static final String MSG_EDIT_PERSON_MIDDLE_NAME = "Введите отчество:";

    public static final String MSG_EDIT_REMINDER_TEXT = "Введите текст напоминания:";
    public static final String MSG_EDIT_REMINDER_DATE = "Введите дату напоминания (в формате дд.мм.гггг):";
    public static final String MSG_EDIT_REMINDER_TIME = "Введите время напоминания (в формате чч.мм):";
    public static final String MSG_EDIT_NEW_REMINDER_TEXT = "Введите новый текст напоминания:";
    public static final String MSG_EDIT_NEW_REMINDER_DATE = "Введите новую дату напоминания (в формате дд.мм.гггг):";
    public static final String MSG_EDIT_NEW_REMINDER_TIME = "Введите новое время напоминания (в формате чч.мм):";

    public static final String MSG_EDIT_PERSON_EMAIL = "Введите email:";
    public static final String MSG_EDIT_PERSON_BIRTH_DATE = "Введите дату рождения (в формате дд.мм.гггг):";
    public static final String MSG_EDIT_PERSON_GENDER = "Выберите пол:";

    public static final String MSG_SUCCESS_REMINDER_ADDITION = ":gift::confetti_ball: Напоминание успешно добавлено! :tada::balloon:\n" +
            "/start - перейти в главное меню\n" +
            "/viewreminders - просмотреть список напоминаний";

    public static final String CBD_EDIT_PERSON_DATA = "cbd_person_data";
    public static final String CBD_EDIT_REGISTER_DATA = "cbd_register_data";

    public static final String CBD_EDIT_LAST_NAME = "cbd_last_name";
    public static final String CBD_EDIT_FIRST_NAME = "cbd_first_name";
    public static final String CBD_EDIT_MIDDLE_NAME = "cbd_middle_name";
    public static final String CBD_EDIT_PERSONAL_DATA_BACK_BUTTON = "cbd_personal_data_back_button";

    public static final String CBD_EDIT_EMAIL = "cbd_email";
    public static final String CBD_EDIT_BIRTH_DATE = "cbd_birth_date";
    public static final String CBD_EDIT_GENDER = "cbd_gender";
    public static final String CBD_EDIT_REGISTER_DATA_BACK_BUTTON = "cbd_register_data_back_button";

    public static final String CBD_MALE_GENDER_SELECT = "cbd_male_gender_select";
    public static final String CBD_FEMALE_GENDER_SELECT = "cbd_female_gender_select";
    public static final String CBD_OTHER_GENDER_SELECT = "cbd_other_gender_select";
    public static final String CBD_GENDER_SELECTOR_BACK_BUTTON = "cbd_gender_selector_back_button";

    public static final String CBD_EDIT_REMINDER_TEXT = "cbd_edit_reminder_text";
    public static final String CBD_EDIT_REMINDER_DATE = "cbd_edit_reminder_date";
    public static final String CBD_EDIT_REMINDER_TIME = "cbd_edit_reminder_time";
    public static final String CBD_EDIT_REMINDER_CLOSE = "cbd_edit_reminder_close";
    public static final String CBD_EDIT_REMINDER_DELETE = "cbd_edit_reminder_delete";
    public static final String CBD_EDIT_REMINDER_BACK_BUTTON = "cbd_edit_reminder_back_button";
    public static final String CBD_EDIT_REMINDER_DELAY = "cbd_edit_reminder_delay";

    public static final String ERR_MSG_WRONG_DATE_FORMAT = ":x::x::x: Не верный формат даты :x::x::x:";
    public static final String ERR_MSG_WRONG_TIME_FORMAT = ":x::x::x: Не верный формат времени :x::x::x:";
    public static final String ERR_MSG_WRONG_EMAIL_FORMAT = ":x::x::x: Не верный формат email :x::x::x:";

    public static final String FORMAT_DATE = "dd.MM.yyyy";
    public static final String FORMAT_TIME = "HH.mm";

    public static final String PREFIX_REMINDERS_LIST = "rem";

    //***
    public static final String DBNS_EDIT_REMINDER = "DBNS_EDIT_REMINDER";
    public static final String DBNS_CURRENT_REMINDER_IDS= "DBNS_CURRENT_REMINDER_IDS";

    public static final String DBNS_PERSONS= "DBNS_PERSONS";
    public static final String DBNS_SIMPLE_REMINDERS= "DBNS_SIMPLE_REMINDERS";
    //***
}
