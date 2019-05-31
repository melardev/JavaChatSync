package com.melardev.chat;


import com.melardev.chat.net.ClientSocketHandler;
import com.melardev.chat.net.packets.PacketChat;
import com.melardev.chat.net.packets.PacketFile;
import com.melardev.chat.ui.mediators.UiMediator;
import com.melardev.chat.ui.swing.mediators.SwingUiMediator;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class ClientApp extends Socket {

    public enum UiMode {
        SWING,
        CONSOLE // Not implemented
    }

    private UiMediator uiMediator;
    private volatile boolean running;
    private volatile boolean loggedIn;
    PacketChat packet;

    private UUID id;
    private String host;
    private int port;
    private ClientSocketHandler clientSocketHandler;
    private String username;


    public ClientApp(UiMode uiMode) {

        switch (uiMode) {
            case SWING: {
                uiMediator = new SwingUiMediator();
                uiMediator.setApp(this);
                break;
            }
            case CONSOLE:
                break;
            default:
                throw new RuntimeException("Invalid Ui Mode");
        }
    }


    public void run() {
        loggedIn = false;

        uiMediator.showLogin();

        new Thread(() -> {
            try {
                clientSocketHandler = new ClientSocketHandler(this, "localhost", 3002);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }


    private void onFileReceived(PacketFile pkt) {

        try {
            String basePath = Paths.get("").toAbsolutePath().toString();
            SimpleDateFormat sdf = new SimpleDateFormat("HH_mm_ss_dd-MM-yy");
            String dstPath = basePath + "/Downloads/" + pkt.getDestination() + "/from" + pkt.getFrom();
            File downloadDir = new File(dstPath);
            if (!downloadDir.exists())
                downloadDir.mkdirs();
            File fos = new File(downloadDir + "/" + sdf.format(new Date()) + pkt.getExtension());
            if (!fos.exists())
                fos.createNewFile();
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(fos));
            bos.write(pkt.getFileContent());
            bos.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        switch (pkt.getFiletype()) {
            case IMAGE:
                break;
            case VIDEO:
            case PDF:
            case RAW:
            case TEXT:

            default:
                break;
        }
    }


    public void tryLogin(String username, String password) {
        clientSocketHandler.tryLoginAsync(username, password);
    }

    public void onNetError(Throwable throwable) {
        uiMediator.showErrorMessage(throwable.getMessage());
    }

    public void onLoginSuccess(String uuid, String username) {
        setId(uuid);
        loggedIn = true;
        uiMediator.showLoginSuccess(username);

    }

    public void onLoginError(String loginMessage) {
        System.out.println("Error on login: " + loginMessage);
        this.uiMediator.showLoginError(username, loginMessage);
    }

    public void onServerAnnouncement(String announcement) {
        uiMediator.showServerMessage(announcement);
    }

    public void onUserDisconnected(String uid, String username) {
        uiMediator.removeUserFromList(uid, username);
    }

    public void onFileReceived(String fromUsername, String destination, byte[] fileContent) {

    }

    public void onPrivateMessageReceived(String fromUsername, String message) {
        uiMediator.showPrivateMessage(fromUsername, message);
    }

    public void onMessageSendRequest(String msgTxt) {
        clientSocketHandler.sendMessageAsync(msgTxt);
    }

    public void sendPrivateMessage(String msgTxt, String destUID) {
        if (destUID != null && destUID.equalsIgnoreCase(this.id.toString())) {
            uiMediator.showErrorMessage("You can not send a message to yourself");
            return;
        }
        clientSocketHandler.sendPrivateMessageAsync(getId().toString(), destUID, msgTxt);
    }

    public void onUsersOnlineListReceived(Map<String, String> users) {
        uiMediator.updateUserList(users);
    }

    public void onPublicMessageReceived(String fromUsername, String message) {
        uiMediator.appendPublicMessage(fromUsername, message);
    }

    public void onSendFileRequested(String absolutePath, String destUserId) {
        clientSocketHandler.sendFileAsync(
                readFile(absolutePath),
                absolutePath.substring(absolutePath.lastIndexOf(".")),
                destUserId);
    }

    private static byte[] readFile(String path) {
        Path p = Paths.get(path);
        try {
            return Files.readAllBytes(p);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    private void setId(String idStr) {
        id = UUID.fromString(idStr);
    }

    public UUID getId() {
        return id;
    }


}
