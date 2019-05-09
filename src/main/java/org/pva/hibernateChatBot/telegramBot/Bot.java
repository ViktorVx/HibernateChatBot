package org.pva.hibernateChatBot.telegramBot;

import org.hibernate.SessionFactory;
import org.pva.hibernateChatBot.person.Person;
import org.pva.hibernateChatBot.person.PersonDao;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Flag;
import org.telegram.abilitybots.api.objects.Privacy;
import org.telegram.abilitybots.api.objects.Reply;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.function.Consumer;
import java.util.function.Predicate;

import static org.telegram.abilitybots.api.objects.Flag.MESSAGE;
import static org.telegram.abilitybots.api.objects.Flag.REPLY;
import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;
import static org.telegram.abilitybots.api.util.AbilityUtils.getChatId;

public class Bot extends AbilityBot {

    private static final String BOT_TOKEN = System.getenv("TELEGRAM_TOKEN");
    private static final String BOT_USERNAME = "ReminderVxBot";
    private static final Integer BOT_CREATOR_ID = Integer.valueOf(System.getenv("BOT_CREATOR_ID"));
    private final ResponseHandler responseHandler;
    private final PersonDao personDao;

    public Bot(SessionFactory sessionFactory) {
        super(BOT_TOKEN, BOT_USERNAME);
        responseHandler = new ResponseHandler(sender, db);
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

    //*** REPLYES TO BUTTONS AND MESSAGES ******************************************************************************

    public Reply replyToButtons() {
        Consumer<Update> action = upd -> {
            try {
                Person person = personDao.findByUserId((long) upd.getCallbackQuery().getFrom().getId());
                responseHandler.replyToButtons(getChatId(upd), upd.getCallbackQuery().getData(), upd, person);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        };
        return Reply.of(action, Flag.CALLBACK_QUERY);
    }

    public Reply replyToMsg() {
        Consumer<Update> action = upd -> {
            try {
                Person person = personDao.findByUserId((long) upd.getMessage().getFrom().getId());
                responseHandler.replyToMsg(upd, person, personDao);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        };
        return Reply.of(action, Flag.MESSAGE, Flag.REPLY);
    }

    //******************************************************************************************************************

    public Ability replyToEnterReminderText() {
        String msg = "О чем нужно напомнить?";
        return Ability.
                builder().
                name("addsimplereminder").
                info("Введите текст напоминания").
                privacy(PUBLIC).
                locality(ALL).
                input(0).
                action(ctx -> {
                    try {
                        sender.execute(new SendMessage()
                                .setText(msg).setChatId(ctx.chatId()).setReplyMarkup(KeyboardFactory.getForceReplyKeyboard()));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }).
                reply(upd -> {
                            String text = upd.getMessage().getText();
                            System.out.println(text);
                        },
                        MESSAGE,
                        REPLY,
                        isReplyToBot(),
                        isReplyToMessage(msg)).
                build();
    }

    public Ability replyToEnterReminderDate() {
        String msg = "Когда нужно напомнить (дата в формате дд.ММ.гггг)?";
        return Ability.
                builder().
                name("addsimplereminderDate").
                info("Введите дату напоминания").
                privacy(Privacy.ADMIN).
                locality(ALL).
                input(0).
                action(ctx -> {
                    try {
                        sender.execute(new SendMessage()
                                .setText(msg).setChatId(ctx.chatId()).setReplyMarkup(KeyboardFactory.getForceReplyKeyboard()));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }).
                reply(upd -> {
                            try {
                                /*simpleReminder.setRemindDate(new SimpleDateFormat("dd.MM.yyyy").parse(upd.getMessage().getText()));*/
                                System.out.println(upd.getMessage().getText());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        },
                        MESSAGE,
                        REPLY,
                        isReplyToBot(),
                        isReplyToMessage(msg)).
                build();
    }

    private Predicate<Update> isReplyToMessage(String message) {
        return upd -> {
            Message reply = upd.getMessage().getReplyToMessage();
            return reply!=null && reply.hasText() && reply.getText().equalsIgnoreCase(message);
        };
    }

    private Predicate<Update> isReplyToBot() {
        return upd -> upd.getMessage().getReplyToMessage().getFrom().getUserName().equalsIgnoreCase(getBotUsername());
    }

    @Deprecated
    public Ability useDatabaseToCountPerUser() {
        return Ability.builder()
                .name("count")
                .info("Increments a counter per user")
                .locality(ALL)
                .privacy(PUBLIC)
                .input(0)
                .action(ctx -> {
                    responseHandler.replyToCount(ctx.chatId());
                })
                .build();
    }


}
