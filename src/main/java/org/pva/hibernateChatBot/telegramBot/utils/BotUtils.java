package org.pva.hibernateChatBot.telegramBot.utils;

import org.pva.hibernateChatBot.telegramBot.constants.ConstantStorage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BotUtils {

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                    "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(ConstantStorage.FORMAT_DATE);

    public static Boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static Date isValidDate(String birthDate) {
        Date date;
        try {
            date = SIMPLE_DATE_FORMAT.parse(birthDate);
        } catch (ParseException e) {
            return null;
        }
        return date;
    }

    public static Map<String, Integer> isValidTime(String reminderTimeText) {
        List<String> timeList = Arrays.asList(reminderTimeText.split("\\."));
        if (timeList.size() != 2) return null;
        Integer hours;
        Integer minutes;
        try {
            hours = Integer.valueOf(timeList.get(0));
            minutes = Integer.valueOf(timeList.get(1));
        } catch (Exception e) {
            return null;
        }
        if (hours < 0 || hours > 23) return null;
        if (minutes < 0 || minutes > 59) return null;
        Map<String, Integer> resMap = new HashMap<>();
        resMap.put("hours", hours);
        resMap.put("minutes", minutes);
        return resMap;
    }
}
