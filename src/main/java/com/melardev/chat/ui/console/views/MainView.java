package com.melardev.chat.ui.console.views;

import com.melardev.chat.ui.console.Console;

public class MainView extends Console {


    public void showMain() {

    }

    public void showAbout() {
        System.out.println("Chat app coded by MelarDev");
    }

    public void showPrivate() {
    }


    public void showSuccessMessage(String message) {
        System.out.println(message);
    }

    public void showErrorMessage(String errorMessage) {
        System.err.println(errorMessage);
    }

    public void appendMessage(String message) {
        System.out.println(message);
    }
}
