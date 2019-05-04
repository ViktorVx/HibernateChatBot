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
                    String username = message.getChat().getUserName();
                    sendMsg(message, String.format("Привет, %s)!", username == null ? "безымянный пользователь" : username));
                    break;
                default: break;
            }
        }
    }

    public void sendMsg(Message message, String text) {
        SendMessage sendMsg = new SendMessage();
        sendMsg.enableMarkdown(true);
        sendMsg.setText(text);
        sendMsg.setChatId(message.getChatId());
        setButtons(sendMsg);
//        sendMsg.setReplyToMessageId(message.getMessageId());
        try {
            execute(sendMsg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardRow1 = new KeyboardRow();
        keyboardRow1.add(new KeyboardButton("/start"));
//        keyboardRow1.add(new KeyboardButton("Пока!"));

        keyboardRowList.add(keyboardRow1);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
    }

    @Override
    public String getBotUsername() {
        return "ReminderVxBot";
    }

    @Override
    public String getBotToken() {
        String token = null;
        try {
            token = Files.readAllLines(Paths.get(new Main().getClass().getClassLoader().getResource("token.txt").toURI()),
                    StandardCharsets.UTF_8).get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }
}
