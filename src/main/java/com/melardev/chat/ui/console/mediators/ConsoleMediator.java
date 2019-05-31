package com.melardev.chat.ui.console.mediators;

import com.melardev.chat.ClientApp;
import com.melardev.chat.ui.console.views.MainView;
import com.melardev.chat.ui.mediators.UiMediator;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConsoleMediator implements UiMediator {

    private Map<String, String> liveUsers;

    public enum View {
        MAIN, LOGIN, PRIVATE, ABOUT
    }

    private MainView mainView;
    View currentView;

    private ClientApp app;

    public ConsoleMediator() {
        mainView = new MainView();
        this.currentView = View.MAIN;
        this.liveUsers = new HashMap<>();
    }

    @Override
    public void showLogin() {
        Scanner scan = new Scanner(System.in);
        System.out.println("type your username");
        String username = scan.nextLine();
        System.out.println("type your password");
        String password = scan.nextLine();
        app.tryLogin(username, password);
    }

    @Override
    public void showMain() {
        mainView.showMain();
    }

    @Override
    public void showAbout() {
        mainView.showAbout();
    }


    @Override
    public void loginRequested(String username, String password) {
        app.tryLogin(username, password);
    }

    @Override
    public void setApp(ClientApp clientApp) {
        this.app = clientApp;
    }

    @Override
    public void showLoginSuccess(String username) {
        this.mainView.showSuccessMessage("You successfully logged in as " + username);
    }

    @Override
    public void showLoginError(String username, String message) {
        this.mainView.showErrorMessage(message);
    }

    @Override
    public void showServerMessage(String announcement) {
        System.out.println("[Server]: " + announcement);
    }

    @Override
    public void showErrorMessage(String message) {
        System.err.println(message);
    }

    @Override
    public void removeUserFromList(String uid, String username) {

    }

    @Override
    public void showPrivateMessage(String fromUsername, String message) {
        this.mainView.appendMessage("[Private] " + fromUsername + ": " + message);
    }

    @Override
    public void sendPublicMessageRequested(String msgTxt) {
        this.app.onMessageSendRequest(msgTxt);
    }

    @Override
    public void sendPrivateMessageRequested(String msgTxt, String destUID) {
        this.app.sendPrivateMessage(msgTxt, destUID);
    }

    @Override
    public void updateUserList(Map<String, String> users) {
        this.liveUsers = users;
    }

    @Override
    public void appendPublicMessage(String fromUsername, String message) {
        this.mainView.appendMessage(fromUsername + ": " + message);
    }

    @Override
    public void onSendFileRequested(String absolutePath, String destUserId) {
        this.app.onSendFileRequested(absolutePath, destUserId);
    }
}
