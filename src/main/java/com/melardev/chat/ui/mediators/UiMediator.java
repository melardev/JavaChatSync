package com.melardev.chat.ui.mediators;

import com.melardev.chat.ClientApp;

import java.util.Map;

public interface UiMediator {

    // Coming from App
    void showLogin();

    void showMain();

    void showAbout();

    void setApp(ClientApp clientApp);

    void showLoginError(String username, String message);

    void showLoginSuccess(String message);

    void showServerMessage(String announcement);

    void showErrorMessage(String message);

    void updateUserList(Map<String, String> users);

    void appendPublicMessage(String fromUsername, String message);

    void showPrivateMessage(String fromUsername, String message);

    void removeUserFromList(String uid, String username);

    // Coming from the UI
    void loginRequested(String username, String password);

    void sendPublicMessageRequested(String msgTxt);

    void sendPrivateMessageRequested(String msgTxt, String destUID);

    void onSendFileRequested(String absolutePath, String destUserId);
}
