package org.pva.hibernateChatBot.telegramBot;

import org.pva.hibernateChatBot.Main;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        String text = message.getText();
        if (message != null && message.hasText()) {
            switch (text) {
                case "/start":
                    startHandler(message);
                    break;
                case "/help":
                    helpHandler(message);
                    break;
                default:
                    break;
            }
        }
    }

    private void helpHandler(Message message) {
        String MESSAGE_TEXT = "Доступные команды:\n" +
                "/addSimpleReminder - добавить простое напоминание\n" +
                "/addCircleReminder - добавить циклическое напоминание\n" +
                "/viewActiveReminders - показать активные напоминания\n" +
                "/viewCircleReminders - показать все циклические напоминания\n" +
                "/viewClosestReminders - показать ближайшие напоминания\n" +
                "Настройки:\n" +
                "/start - начало работы\n" +
                "/settings - настройки пользователя\n" +
                "/help - помощь";
        SendMessage sendMsg = new SendMessage();
        sendMsg.enableMarkdown(true);
        sendMsg.setText(MESSAGE_TEXT);
        sendMsg.setChatId(message.getChatId());
        try {
            execute(sendMsg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startHandler(Message message) {
        String MESSAGE_TEXT = "Привет, %s!\n" +
                "Ты зашет в бот-напоминайку!\n" +
                "Очем напомнить?\n" +
                "/addSimpleReminder - добавить простое напоминание\n" +
                "/addCircleReminder - добавить циклическое напоминание\n" +
                "/viewActiveReminders - показать активные напоминания\n" +
                "/viewCircleReminders - показать все циклические напоминания\n" +
                "/viewClosestReminders - показать ближайшие напоминания\n" +
                "Настройки:\n" +
                "/start - начало работы\n" +
                "/settings - настройки пользователя\n" +
                "/help - помощь";
        String username = message.getChat().getUserName();
        SendMessage sendMsg = new SendMessage();
        sendMsg.enableMarkdown(true);
        sendMsg.setText(String.format(MESSAGE_TEXT, username == null ? "безымянный пользователь" : username));
        sendMsg.setChatId(message.getChatId());
//        setButtons(sendMsg);
//        sendMsg.setReplyToMessageId(message.getMessageId());
        try {
            execute(sendMsg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void setButtons(SendMessage sendMessage) {
//        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
//        sendMessage.setReplyMarkup(replyKeyboardMarkup);
//        replyKeyboardMarkup.setSelective(true);
//        replyKeyboardMarkup.setResizeKeyboard(true);
//        replyKeyboardMarkup.setOneTimeKeyboard(false);
//
//        List<KeyboardRow> keyboardRowList = new ArrayList<>();
//        KeyboardRow keyboardRow1 = new KeyboardRow();
//        keyboardRow1.add(new KeyboardButton("/start"));
////        keyboardRow1.add(new KeyboardButton("Пока!"));
//
//        keyboardRowList.add(keyboardRow1);
//        replyKeyboardMarkup.setKeyboard(keyboardRowList);
//    }

    @Override
    public String getBotUsername() {
        return "ReminderVxBot";
    }

    @Override
    public String getBotToken() {
        return System.getenv("TELEGRAM_TOKEN");
    }
}
