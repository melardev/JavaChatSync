package com.melardev.chat.net;

import com.melardev.chat.ClientApp;
import com.melardev.chat.net.packets.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

public class ClientSocketHandler implements Runnable {

    private final String host;
    private final int port;
    private Thread threadWrite;
    private ObjectOutputStream os;
    private ObjectInputStream is;
    private boolean loginSuccess;
    private volatile boolean running;
    private ClientApp app;
    private LinkedBlockingQueue<PacketChat> packetsPendingForSend;
    private String username;
    private Socket socket;
    private Thread netThread;

    public ClientSocketHandler(ClientApp clientApp, String host, int port) throws IOException {
        app = clientApp;
        this.host = host;
        this.port = port;
        running = false;
        loginSuccess = false;
        packetsPendingForSend = new LinkedBlockingQueue<>(50);
        socket = new Socket();
    }


    private Runnable loopWrite = new Runnable() {
        @Override
        public void run() {
            PacketChat packet;
            while (running) {
                try {
                    packet = packetsPendingForSend.take();
                    sendPacket(packet);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    @Override
    public void run() {
        PacketChat packet;

        try {
            socket.connect(new InetSocketAddress(host, port));
            os = new ObjectOutputStream(socket.getOutputStream());
            is = new ObjectInputStream(socket.getInputStream());

            threadWrite = new Thread(loopWrite);
            threadWrite.start();

            while (running) {
                try {
                    packet = (PacketChat) is.readObject();
                    handleIncomingPacket(packet);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            try {
                e.printStackTrace();
                app.onNetError(e);
                running = false;
                this.socket.close();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                app.onLoginError(e1.toString());
            }
        }


    }

    public ObjectOutputStream getObjectOutputStream() {
        return os;
    }

    public ObjectInputStream getObjectInputStream() {
        return is;
    }

    private void sendPacket(PacketChat packetChat) {
        try {
            os.writeObject(packetChat);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleIncomingPacket(PacketChat packet) {
        switch (packet.getPacketType()) {
            case LOGIN_RESPONSE:
                loginSuccess = ((PacketLoginResponse) packet).isSuccess();
                if (loginSuccess) {
                    String uid = ((PacketLoginResponse) packet).getAssignedUUID();
                    app.onLoginSuccess(uid, username);
                } else {
                    app.onLoginError(((PacketLoginResponse) packet).getLoginMessage());
                }
                break;
            case ANNOUNCE_JOIN:
                String announcement = ((PacketAnnounce) packet).getAnnouncement();
                app.onServerAnnouncement(announcement);
                break;
            case ANNOUNCE_DISCONNECT:
                String uid = ((PacketChatAnnounceDisconnect) packet).getUID();
                String nickName = ((PacketChatAnnounceDisconnect) packet).getNickName();
                app.onUserDisconnected(uid, nickName);
                break;
            case ANNOUNCE_BANNED:
                break;
            case ANNOUNCE:
                break;
            case UPDATE_CONTACTS:
                Map<String, String> users = ((PacketUserList) packet).getUsers();
                app.onUsersOnlineListReceived(users);
                break;
            case UPDATE_CONTACTS_DISCONNECT:
                // gui.removeUsersJlist(pkt.getMessage());
                break;
            case PUBLIC_MESSAGE: {
                String fromUsnermae = packet.getFrom();
                String message = ((PacketPublicMessage) packet).getMessage();
                app.onPublicMessageReceived(fromUsnermae, message);
                break;
            }
            case PRIVATE_MESSAGE: {
                String fromUsername = packet.getFrom();
                String message = ((PacketPrivateMessage) packet).getMessage();
                app.onPrivateMessageReceived(fromUsername, message);
                break;
            }
            case FILE_ATTACH:
                PacketFile packetFile = (PacketFile) packet;
                app.onFileReceived(packetFile.getFrom(), packetFile.getDestination(), packetFile.getFileContent());
                break;
            default:
                break;
        }

    }

    public void sendMessageAsync(String msgTxt) {
        packetsPendingForSend.offer(new PacketPublicMessage(msgTxt));
    }

    public void sendPrivateMessageAsync(String fromUID, String destUID, String msgTxt) {
        packetsPendingForSend.offer(new PacketPrivateMessage(msgTxt, fromUID, destUID));
    }

    public void sendFileAsync(byte[] fileContent, String extension, String destUserId) {
        packetsPendingForSend.offer(new PacketFile(PacketFile.PacketFileType.AUDIO, fileContent, extension, destUserId));
    }

    public void tryLoginAsync(String username, String password) {
        startAsync();
        this.username = username;
        packetsPendingForSend.offer(new PacketLoginRequest(username, password));
    }

    private void startAsync() {
        running = true;
        netThread = new Thread(this, "NetThread");
        netThread.start();
    }
}
