package com.melardev.chat.ui.swing.mediators;

import com.melardev.chat.ClientApp;
import com.melardev.chat.ui.mediators.UiMediator;
import com.melardev.chat.ui.swing.AppWindow;
import com.melardev.chat.ui.swing.ClientChatSwingUi;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SwingUiMediator implements UiMediator {
    private ClientApp app;
    private List<AppWindow> openedWindows;

    public SwingUiMediator() {
        this.openedWindows = new ArrayList<>();
    }

    @Override
    public void showLogin() {
        runOnSwingUiThread(() -> {
            try {
                ClientChatSwingUi frame = new ClientChatSwingUi(this);
                frame.setVisible(true);
                openedWindows.add(frame);
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        });
    }

    private void runOnSwingUiThread(Runnable runnable) {
        SwingUtilities.invokeLater(runnable);
    }

    @Override
    public void showMain() {
        runOnSwingUiThread(() -> {

        });
    }

    @Override
    public void showAbout() {
        runOnSwingUiThread(() -> {

        });
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
        ClientChatSwingUi window = (ClientChatSwingUi) getWindow(ClientChatSwingUi.class);
        if (window != null) {
            runOnSwingUiThread(() -> window.onLoggedIn(username));
        }
    }

    @Override
    public void showLoginError(String username, String message) {
        ClientChatSwingUi window = (ClientChatSwingUi) getWindow(ClientChatSwingUi.class);
        if (window == null)
            return;
        runOnSwingUiThread(() -> {
            window.setTextAreaMsg("Bad Login");
        });
    }

    @Override
    public void showServerMessage(String announcement) {
        ClientChatSwingUi window = getMainWindow();
        runOnSwingUiThread(() -> {
            window.setTextAreaMsg("Server: " + announcement);
        });
    }

    @Override
    public void showErrorMessage(String message) {
        // TODO: show error message in a separate component other than JTextArea
        ClientChatSwingUi window = getMainWindow();
        runOnSwingUiThread(() -> {
            window.setTextAreaMsg(message);
        });
    }

    @Override
    public void removeUserFromList(String uid, String username) {
        ClientChatSwingUi window = getMainWindow();
        runOnSwingUiThread(() -> {
            window.setTextAreaMsg(String.format("%s just disconnected", username));
        });
    }

    @Override
    public void showPrivateMessage(String fromUsername, String message) {
        ClientChatSwingUi window = getMainWindow();
        runOnSwingUiThread(() -> {
            window.setTextAreaMsg("Personal Msg From " + fromUsername + " : " + message);
        });
    }

    @Override
    public void sendPublicMessageRequested(String msgTxt) {
        app.onMessageSendRequest(msgTxt);
    }

    @Override
    public void sendPrivateMessageRequested(String msgTxt, String destUID) {
        app.sendPrivateMessage(msgTxt, destUID);
    }

    @Override
    public void updateUserList(Map<String, String> users) {
        ClientChatSwingUi window = getMainWindow();
        runOnSwingUiThread(() -> {
            window.addUsersJList(users);
        });
    }

    @Override
    public void appendPublicMessage(String fromUsername, String message) {
        ClientChatSwingUi window = getMainWindow();
        runOnSwingUiThread(() -> {
            window.setTextAreaMsg(fromUsername + " : " + message);
        });
    }

    @Override
    public void onSendFileRequested(String absolutePath, String destUserId) {


        app.onSendFileRequested(absolutePath, destUserId);
    }

    private ClientChatSwingUi getMainWindow() {
        return (ClientChatSwingUi) getWindow(ClientChatSwingUi.class);
    }

    private <T extends AppWindow> AppWindow getWindow(Class<T> clientChatSwingUiClass) {
        for (AppWindow window : openedWindows) {
            if (window.getClass().equals(clientChatSwingUiClass))
                return window;
        }
        return null;
    }
}
