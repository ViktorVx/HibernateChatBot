package org.pva.hibernateChatBot.telegramBot;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ForceReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

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
        rowInline.add(new InlineKeyboardButton().setText("Изменить фамилию").setCallbackData("edit_last_name"));
        rowsInline.add(rowInline);

        rowInline = new ArrayList<>();
        rowInline.add(new InlineKeyboardButton().setText("Изменить имя").setCallbackData("edit_first_name"));
        rowsInline.add(rowInline);

        rowInline = new ArrayList<>();
        rowInline.add(new InlineKeyboardButton().setText("Изменить отчество").setCallbackData("edit_middle_anme"));
        rowsInline.add(rowInline);

        inlineKeyboard.setKeyboard(rowsInline);
        return inlineKeyboard;
    }

    public static InlineKeyboardMarkup getInfoEditKeyboard() {
        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        rowInline.add(new InlineKeyboardButton().setText("Персональные данные").setCallbackData("edit_person_data"));
        rowsInline.add(rowInline);

        rowInline = new ArrayList<>();
        rowInline.add(new InlineKeyboardButton().setText("Регистрационные данные").setCallbackData("edit_register_name"));
        rowsInline.add(rowInline);

        inlineKeyboard.setKeyboard(rowsInline);
        return inlineKeyboard;
    }

    public static ForceReplyKeyboard getForceReplyKeyboard() {
        ForceReplyKeyboard forceReplyKeyboard = new ForceReplyKeyboard();
        return forceReplyKeyboard;
    }

}
