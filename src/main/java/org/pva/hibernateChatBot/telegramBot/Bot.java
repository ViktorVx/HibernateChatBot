package org.pva.hibernateChatBot.telegramBot;

import com.vdurmont.emoji.EmojiParser;
import org.hibernate.SessionFactory;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.pva.hibernateChatBot.constants.ConstantStorage;
import org.pva.hibernateChatBot.enums.Gender;
import org.pva.hibernateChatBot.person.Person;
import org.pva.hibernateChatBot.person.PersonDao;
import org.pva.hibernateChatBot.reminder.Reminder;
import org.pva.hibernateChatBot.reminder.SimpleReminder;
import org.pva.hibernateChatBot.utils.BotUtils;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Flag;
import org.telegram.abilitybots.api.objects.Reply;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;
import static org.telegram.abilitybots.api.util.AbilityUtils.getChatId;

public class Bot extends AbilityBot {

    private static final String BOT_TOKEN = System.getenv("TELEGRAM_TOKEN");
    private static final String BOT_USERNAME = "ReminderVxBot";
    private static final Integer BOT_CREATOR_ID = Integer.valueOf(System.getenv("BOT_CREATOR_ID"));
    private final PersonDao personDao;
    private final Map<String, Map<String, String>> reminderMap = db.getMap(ConstantStorage.DBNS_SIMPLE_REMINDERS);

    public Bot(SessionFactory sessionFactory) {
        super(BOT_TOKEN, BOT_USERNAME);
        personDao = new PersonDao(sessionFactory);
    }

    @Override
    public int creatorId() {
        return BOT_CREATOR_ID;
    }

    //*** COMMAND ABILITIES ********************************************************************************************

    public Ability replyToStart() {
        return Ability
                .builder()
                .name("start")
                .info("Starts the bot!")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> {
                    Person person = personDao.findByUserId((long) ctx.user().getId());
                    if (person == null) {
                        User user = ctx.user();
                        person = new Person();
                        person.setUserId((long) user.getId());
                        person.setFirstName(user.getFirstName());
                        person.setLastName(user.getLastName());
                        person.setLogin(user.getUserName().concat("@").concat(String.valueOf(user.getId())));
                        personDao.save(person);
                    }

                    String MESSAGE = String.format("Привет, %s!\n" +
                            "Ты зашел в бот-напоминайку!\n" +
                            "О чем напомнить?\n" +
                            "/addsimplereminder - добавить простое напоминание\n" +
                            "/viewreminders - показать ближайшие напоминания\n" +
                            "Настройки:\n" +
                            "/start - начало работы\n" +
                            "/info - информация о пользователе\n" +
                            "/help - помощь", ctx.user().getUserName());
                    try {
                        sender.execute(new SendMessage()
                                .setText(MESSAGE)
                                .setChatId(ctx.chatId()));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                })
                .build();
    }

    public Ability replyToInfo() {
        return Ability
                .builder()
                .name("info")
                .info("Edit user's info!")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> {
                    String MESSAGE = String.format("%s, заполните данные о себе!\n", ctx.user().getUserName());
                    try {
                        sender.execute(new SendMessage()
                                .setText(MESSAGE)
                                .setChatId(ctx.chatId()).setReplyMarkup(KeyboardFactory.getInfoEditKeyboard()));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                })
                .build();
    }

    public Ability replyToHelp() {
        return Ability
                .builder()
                .name("help")
                .info("Help me!!!")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> {
                    String MESSAGE =
                            "Доступны следующие команды:\n" +
                                    "/start - начало работы\n" +
                                    "/info - информация о пользователе\n" +
                                    "/help - помощь\n" +
                                    "/addsimplereminder - добавить простое напоминание\n" +
                                    "/viewreminders - показать напоминания";
                    try {
                        sender.execute(new SendMessage()
                                .setText(MESSAGE)
                                .setChatId(ctx.chatId()));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                })
                .build();
    }

    public Ability replyToAddSimpleReminder() {
        return Ability
                .builder()
                .name("addsimplereminder")
                .info("Add simple reminder")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> {
                    String MESSAGE = ConstantStorage.MSG_EDIT_REMINDER_TEXT;
                    try {
                        sender.execute(new SendMessage()
                                .setText(MESSAGE)
                                .setChatId(ctx.chatId()).setReplyMarkup(KeyboardFactory.getForceReplyKeyboard()));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                })
                .build();
    }

    public Ability replyToViewReminders() {
        return Ability
                .builder()
                .name("viewreminders")
                .info("View reminders list")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> {
                    Person person = personDao.findByUserId((long) ctx.user().getId());
                    String message = EmojiParser.parseToUnicode(":calendar: Список напоминаний (/addsimplereminder):\n");
                    List<Reminder> reminderList = person.getActiveRimindersList();
                    Collections.sort(reminderList, Comparator.comparing(o -> ((SimpleReminder) o).getRemindDate()));
                    for (Reminder reminder : reminderList) {
                        SimpleReminder simpleReminder = (SimpleReminder) reminder;
                        message = message.concat(String.format( "/n%d %s %s - %s\n",
                                reminderList.indexOf(simpleReminder) + 1,
                                new SimpleDateFormat(ConstantStorage.FORMAT_DATE).format(simpleReminder.getRemindDate()),
                                new SimpleDateFormat(ConstantStorage.FORMAT_TIME).format(simpleReminder.getRemindDate()),
                                simpleReminder.getText() ));
                    }
                    try {
                        sender.execute(new SendMessage()
                                .setText(message)
                                .setChatId(ctx.chatId()));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                })
                .build();
    }

    //*** REPLYES TO BUTTONS AND MESSAGES ******************************************************************************

    public Reply replyToButtons() {
        Consumer<Update> action = upd -> {
            Person person = personDao.findByUserId((long) upd.getCallbackQuery().getFrom().getId());
            //****************************************************
            long chatId = getChatId(upd);
            switch (upd.getCallbackQuery().getData()) {
                case ConstantStorage.CBD_EDIT_PERSON_DATA:
                    EditPersonalDataView.replyToEditPersonalData(upd, person, sender);
                    break;
                case ConstantStorage.CBD_EDIT_REGISTER_DATA:
                    EditPersonRegisterDataView.replyToEditRegisterData(upd, person, sender);
                    break;
                case ConstantStorage.MSG_EDIT_PERSON_LAST_NAME:
                    EditPersonalDataView.replyToEditLastName(chatId, sender);
                    break;
                case ConstantStorage.MSG_EDIT_PERSON_FIRST_NAME:
                    EditPersonalDataView.replyToEditFirstName(chatId, sender);
                    break;
                case ConstantStorage.MSG_EDIT_PERSON_MIDDLE_NAME:
                    EditPersonalDataView.replyToEditMiddleName(chatId, sender);
                    break;
                case ConstantStorage.CBD_EDIT_PERSONAL_DATA_BACK_BUTTON:
                    EditPersonalDataView.replyToEditInfoBackButton(upd, sender);
                    break;
                case ConstantStorage.CBD_EDIT_EMAIL:
                    EditPersonRegisterDataView.replyToEmail(chatId, sender);
                    break;
                case ConstantStorage.CBD_EDIT_BIRTH_DATE:
                    EditPersonRegisterDataView.replyToBirthDate(chatId, sender);
                    break;
                case ConstantStorage.CBD_EDIT_GENDER:
                    EditPersonRegisterDataView.replyToGender(chatId, upd, sender);
                    break;
                case ConstantStorage.CBD_EDIT_REGISTER_DATA_BACK_BUTTON:
                    EditPersonalDataView.replyToEditInfoBackButton(upd, sender);
                    break;
                case ConstantStorage.CBD_GENDER_SELECTOR_BACK_BUTTON:
                    EditPersonRegisterDataView.replyToRegisterBackButton(upd, person, sender);
                    break;
                case ConstantStorage.CBD_MALE_GENDER_SELECT:
                    person.setGender(Gender.MALE);
                    personDao.update(person);
                    EditPersonRegisterDataView.replyToEditRegisterData(upd, person, sender);
                    break;
                case ConstantStorage.CBD_FEMALE_GENDER_SELECT:
                    person.setGender(Gender.FEMALE);
                    personDao.update(person);
                    EditPersonRegisterDataView.replyToEditRegisterData(upd, person, sender);
                    break;
                case ConstantStorage.CBD_OTHER_GENDER_SELECT:
                    person.setGender(Gender.UNKNOWN);
                    personDao.update(person);
                    EditPersonRegisterDataView.replyToEditRegisterData(upd, person, sender);
                    break;


            }
            //****************************************************
        };
        return Reply.of(action, Flag.CALLBACK_QUERY);
    }

    public Reply replyToMsg() {
        Consumer<Update> action = upd -> {
            Person person = personDao.findByUserId((long) upd.getMessage().getFrom().getId());
            long userId = upd.getMessage().getFrom().getId();
            long chatId = upd.getMessage().getChatId();
            SendMessage sendMessage = new SendMessage();
            //*********************************************************
            Message message = upd.getMessage();
            Message reply = upd.getMessage().getReplyToMessage();
            String msg = "";
            if (reply != null && reply.hasText()) {
                msg = reply.getText();
            }

            switch (msg) {
                case ConstantStorage.MSG_EDIT_PERSON_LAST_NAME:
                    person.setLastName(message.getText());
                    personDao.update(person);
                    EditPersonalDataView.replyToEditPersonalData(upd, person, sender);
                    break;
                case ConstantStorage.MSG_EDIT_PERSON_FIRST_NAME:
                    person.setFirstName(message.getText());
                    personDao.update(person);
                    EditPersonalDataView.replyToEditPersonalData(upd, person, sender);
                    break;
                case ConstantStorage.MSG_EDIT_PERSON_MIDDLE_NAME:
                    person.setMiddleName(message.getText());
                    personDao.update(person);
                    EditPersonalDataView.replyToEditPersonalData(upd, person, sender);
                    break;
                case ConstantStorage.MSG_EDIT_PERSON_EMAIL:
                    if (BotUtils.isValidEmail(message.getText())) {
                        person.setEmail(message.getText());
                    } else {
                        try {
                            sender.execute(new SendMessage().setChatId(chatId).
                                    setText(EmojiParser.parseToUnicode(ConstantStorage.ERR_MSG_WRONG_EMAIL_FORMAT)));
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    }
                    personDao.update(person);
                    EditPersonRegisterDataView.replyToEditRegisterData(upd, person, sender);
                    break;
                case ConstantStorage.MSG_EDIT_PERSON_BIRTH_DATE:
                    Date birthDate = BotUtils.isValidDate(message.getText());
                    if (birthDate != null) {
                        person.setBirthDate(birthDate);
                    } else {
                        try {
                            sender.execute(new SendMessage().setChatId(chatId).
                                    setText(EmojiParser.parseToUnicode(ConstantStorage.ERR_MSG_WRONG_DATE_FORMAT)));
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    }
                    personDao.update(person);
                    EditPersonRegisterDataView.replyToEditRegisterData(upd, person, sender);
                    break;
                case ConstantStorage.MSG_EDIT_REMINDER_TEXT:
                    String reminderText = message.getText();

                    Map<String, String> newRemMap = new HashMap<>();
                    newRemMap.put("text", reminderText);
                    reminderMap.put(String.valueOf(userId), newRemMap);

                    try {
                        sender.execute(new SendMessage().setChatId(chatId).
                                setText(ConstantStorage.MSG_EDIT_REMINDER_DATE).
                                setReplyMarkup(KeyboardFactory.getForceReplyKeyboard()));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    break;
                case ConstantStorage.MSG_EDIT_REMINDER_DATE:
                    String reminderDateText = message.getText();
                    Date reminderDate = BotUtils.isValidDate(reminderDateText);
                    if (reminderDate == null) {
                        sendMessage.setChatId(chatId).
                                setText(EmojiParser.parseToUnicode(ConstantStorage.ERR_MSG_WRONG_DATE_FORMAT));
                    } else {

                        Map<String, String> remMap = reminderMap.get(String.valueOf(userId));
                        remMap.put("date", new SimpleDateFormat(ConstantStorage.FORMAT_DATE).format(reminderDate));
                        reminderMap.put(String.valueOf(userId), remMap);

                        sendMessage.setChatId(chatId).
                                setText(ConstantStorage.MSG_EDIT_REMINDER_TIME).
                                setReplyMarkup(KeyboardFactory.getForceReplyKeyboard());
                    }
                    try {
                        sender.execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    break;
                case ConstantStorage.MSG_EDIT_REMINDER_TIME:
                    String reminderTimeText = message.getText();
                    Map<String, Integer> reminderTime = BotUtils.isValidTime(reminderTimeText);
                    if (reminderTime == null) {
                        sendMessage.setChatId(chatId).
                                setText(EmojiParser.parseToUnicode(ConstantStorage.ERR_MSG_WRONG_TIME_FORMAT));
                    } else {
                        Map<String, String> remMap = reminderMap.get(String.valueOf(userId));
                        SimpleReminder simpleReminder = new SimpleReminder();
                        simpleReminder.setCreationDate(new Date());
                        simpleReminder.setText(remMap.get("text"));
                        simpleReminder.setComplete(false);

                        String stringDateTime = remMap.get("date").concat(" ").concat(reminderTimeText);
                        DateTimeFormatter dateTimeFormatter
                                = DateTimeFormat.forPattern("dd.MM.yyyy HH.mm");
                        DateTime remDateTime = DateTime.parse(stringDateTime, dateTimeFormatter);
                        simpleReminder.setRemindDate(remDateTime.toDate());

                        person.getReminderList().add(simpleReminder);
                        personDao.update(person);

                        sendMessage.setChatId(chatId).
                                setText(EmojiParser.parseToUnicode(ConstantStorage.MSG_SUCCESS_REMINDER_ADDITION));
                    }
                    try {
                        sender.execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    break;
            }
            //*********************************************************
        };
        return Reply.of(action, Flag.MESSAGE, Flag.REPLY);
    }

    public Reply replyToReminderSelection() {
        Consumer<Update> action = upd -> {
            Person person = personDao.findByUserId((long) upd.getMessage().getFrom().getId());
            long userId = upd.getMessage().getFrom().getId();
            long chatId = upd.getMessage().getChatId();
            SendMessage sendMessage = new SendMessage();
            //*********************************************************
            Integer index = Integer.valueOf(upd.getMessage().getText().replace("/n", ""))-1;
            List<Reminder> reminderList = person.getActiveRimindersList();
            Collections.sort(reminderList, Comparator.comparing(o -> ((SimpleReminder) o).getRemindDate()));
            SimpleReminder simpleReminder = (SimpleReminder) reminderList.get(index);

            try {
                sender.execute(new SendMessage().setChatId(chatId).
                        setText(EmojiParser.parseToUnicode(
                                String.format(":memo: Редактировать напоминание:\n" +
                                        "Текст: %s\n" +
                                        "Дата: %s\n" +
                                        "Время: %s\n",
                                        simpleReminder.getText(),
                                        new SimpleDateFormat(ConstantStorage.FORMAT_DATE).format(simpleReminder.getRemindDate()),
                                        new SimpleDateFormat(ConstantStorage.FORMAT_TIME).format(simpleReminder.getRemindDate())))).
                        setReplyMarkup(KeyboardFactory.getEditReminderKeyboard()));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

            //*********************************************************
        };
        return Reply.of(action, isReplyToReminderSelector());
    }

    //*** ADDITIONAL PREDICATES ****************************************************************************************

    private Predicate<Update> isReplyToMessage(String message) {
        return upd -> {
            Message reply = upd.getMessage().getReplyToMessage();
            return reply != null && reply.hasText() && reply.getText().equalsIgnoreCase(message);
        };
    }

    private Predicate<Update> isReplyToBot() {
        return upd -> upd.getMessage().getReplyToMessage().getFrom().getUserName().equalsIgnoreCase(getBotUsername());
    }

    private Predicate<Update> isReplyToReminderSelector() {
        return upd -> upd.getMessage().getText().matches("^/n[0-9]+");
    }

    //******************************************************************************************************************
}
