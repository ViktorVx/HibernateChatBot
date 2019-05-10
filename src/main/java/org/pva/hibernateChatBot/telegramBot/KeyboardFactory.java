package org.pva.hibernateChatBot.telegramBot;

import com.vdurmont.emoji.EmojiParser;
import org.pva.hibernateChatBot.constants.ConstantStorage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ForceReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class KeyboardFactory {

    public static ReplyKeyboard getStartCountKeyboard() {
        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        rowInline.add(new InlineKeyboardButton().setText("Старт").setCallbackData("start"));
        rowInline.add(new InlineKeyboardButton().setText("i++").setCallbackData("count"));
        rowsInline.add(rowInline);
        inlineKeyboard.setKeyboard(rowsInline);
        return inlineKeyboard;

//        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
//        replyKeyboardMarkup.setSelective(true);
//        replyKeyboardMarkup.setResizeKeyboard(true);
//        replyKeyboardMarkup.setOneTimeKeyboard(false);
//
//        List<KeyboardRow> keyboardRowList = new ArrayList<>();
//        KeyboardRow keyboardRow1 = new KeyboardRow();
//        keyboardRow1.add(new KeyboardButton("/start"));
//        keyboardRow1.add(new KeyboardButton("/count"));
//
//        keyboardRowList.add(keyboardRow1);
//        replyKeyboardMarkup.setKeyboard(keyboardRowList);
//        return replyKeyboardMarkup;
    }

    public static InlineKeyboardMarkup getInfoPersonEditKeyboard() {
        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        rowInline.add(new InlineKeyboardButton().setText("Изменить фамилию").setCallbackData(ConstantStorage.CBD_EDIT_LAST_NAME));
        rowsInline.add(rowInline);

        rowInline = new ArrayList<>();
        rowInline.add(new InlineKeyboardButton().setText("Изменить имя").setCallbackData(ConstantStorage.CBD_EDIT_FIRST_NAME));
        rowsInline.add(rowInline);

        rowInline = new ArrayList<>();
        rowInline.add(new InlineKeyboardButton().setText("Изменить отчество").setCallbackData(ConstantStorage.CBD_EDIT_MIDDLE_NAME));
        rowsInline.add(rowInline);

        rowInline = new ArrayList<>();
        rowInline.add(new InlineKeyboardButton().setText(EmojiParser.parseToUnicode(":point_left: Назад")).setCallbackData(ConstantStorage.CBD_EDIT_PERSONAL_DATA_BACK_BUTTON));
        rowsInline.add(rowInline);

        inlineKeyboard.setKeyboard(rowsInline);
        return inlineKeyboard;
    }

    public static InlineKeyboardMarkup getRegisterDataEditKeyboard() {
        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        rowInline.add(new InlineKeyboardButton().setText("Изменить email").setCallbackData(ConstantStorage.CBD_EDIT_EMAIL));
        rowsInline.add(rowInline);

        rowInline = new ArrayList<>();
        rowInline.add(new InlineKeyboardButton().setText("Изменить дату рождения").setCallbackData(ConstantStorage.CBD_EDIT_BIRTH_DATE));
        rowsInline.add(rowInline);

        rowInline = new ArrayList<>();
        rowInline.add(new InlineKeyboardButton().setText("Изменить пол").setCallbackData(ConstantStorage.CBD_EDIT_GENDER));
        rowsInline.add(rowInline);

        rowInline = new ArrayList<>();
        rowInline.add(new InlineKeyboardButton().setText(EmojiParser.parseToUnicode(":point_left: Назад")).setCallbackData(ConstantStorage.CBD_EDIT_REGISTER_DATA_BACK_BUTTON));
        rowsInline.add(rowInline);

        inlineKeyboard.setKeyboard(rowsInline);
        return inlineKeyboard;
    }

    public static InlineKeyboardMarkup getInfoEditKeyboard() {
        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        rowInline.add(new InlineKeyboardButton().setText("Персональные данные").setCallbackData(ConstantStorage.CBD_EDIT_PERSON_DATA));
        rowsInline.add(rowInline);

        rowInline = new ArrayList<>();
        rowInline.add(new InlineKeyboardButton().setText("Регистрационные данные").setCallbackData(ConstantStorage.CBD_EDIT_REGISTER_DATA));
        rowsInline.add(rowInline);

        inlineKeyboard.setKeyboard(rowsInline);
        return inlineKeyboard;
    }

    public static InlineKeyboardMarkup getGenderSelectKeyboard() {
        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        rowInline.add(new InlineKeyboardButton().setText("Мужской").setCallbackData(ConstantStorage.CBD_MALE_GENDER_SELECT));
        rowInline.add(new InlineKeyboardButton().setText("Женский").setCallbackData(ConstantStorage.CBD_FEMALE_GENDER_SELECT));
        rowsInline.add(rowInline);

        rowInline = new ArrayList<>();
        rowInline.add(new InlineKeyboardButton().setText("Другой").setCallbackData(ConstantStorage.CBD_OTHER_GENDER_SELECT));
        rowInline.add(new InlineKeyboardButton().setText("Назад").setCallbackData(ConstantStorage.CBD_GENDER_SELECTOR_BACK_BUTTON));
        rowsInline.add(rowInline);

        inlineKeyboard.setKeyboard(rowsInline);
        return inlineKeyboard;
    }

    public static InlineKeyboardMarkup getEditReminderKeyboard() {
        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        rowInline.add(new InlineKeyboardButton().setText(EmojiParser.parseToUnicode(":lower_left_fountain_pen: Текст")).
                setCallbackData(ConstantStorage.CBD_EDIT_REMINDER_TEXT));
        rowInline.add(new InlineKeyboardButton().setText(EmojiParser.parseToUnicode(":lower_left_fountain_pen: Дату")).
                setCallbackData(ConstantStorage.CBD_EDIT_REMINDER_DATE));
        rowInline.add(new InlineKeyboardButton().setText(EmojiParser.parseToUnicode(":lower_left_fountain_pen: Время")).
                setCallbackData(ConstantStorage.CBD_EDIT_REMINDER_TIME));
        rowsInline.add(rowInline);

        rowInline = new ArrayList<>();
        rowInline.add(new InlineKeyboardButton().setText(EmojiParser.parseToUnicode(":white_check_mark: Выполнить")).
                setCallbackData(ConstantStorage.CBD_EDIT_REMINDER_CLOSE));
        rowInline.add(new InlineKeyboardButton().setText(EmojiParser.parseToUnicode(":x: Удалить")).
                setCallbackData(ConstantStorage.CBD_EDIT_REMINDER_DELETE));
        rowInline.add(new InlineKeyboardButton().setText(EmojiParser.parseToUnicode(":arrow_left: Назад")).
                setCallbackData(ConstantStorage.CBD_EDIT_REMINDER_BACK_BUTTON));
        rowsInline.add(rowInline);

        inlineKeyboard.setKeyboard(rowsInline);
        return inlineKeyboard;
    }

    public static ForceReplyKeyboard getForceReplyKeyboard() {
        ForceReplyKeyboard forceReplyKeyboard = new ForceReplyKeyboard();
        return forceReplyKeyboard;
    }

}
