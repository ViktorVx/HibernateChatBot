package org.pva.hibernateChatBot.communication;

public interface Communication {

    void welcome();

    boolean isRegistresUser();

    void checkOnExit(String input);

    void userRegistration();

    static void handleCommand(String command) {

    }

}
