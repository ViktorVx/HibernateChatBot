package org.pva.hibernateChatBot.communication;

import java.util.Scanner;

public class TerminalCommunication implements Communication {


    @Override
    public void checkOnExit(String input) {
        if (input.equals("exit")) System.exit(0);
    }

    @Override
    public void userRegistration() {
        System.out.println("Введите Ваш логин:");
        Scanner scanner = new Scanner(System.in);
        String login = scanner.nextLine();
        //todo остановился здесь!
    }

    @Override
    public void welcome() {
        System.out.println("Приветсвуем Вас в НАПОМИНАЛКЕ!");
    }

    @Override
    public boolean isRegistresUser() {
        System.out.println("Вы уже зарегистрированы в системе?");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().toLowerCase();
        checkOnExit(input);

        if (input.equals("y") || input.equals("yes") || input.equals("д") || input.equals("да")) return true;
        return false;
    }
}
