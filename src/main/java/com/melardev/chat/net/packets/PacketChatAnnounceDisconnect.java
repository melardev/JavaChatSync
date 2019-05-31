package com.melardev.chat.net.packets;

public class PacketChatAnnounceDisconnect extends PacketChat {
    private final String nicknameDisconnected;
    private String uid;

    public PacketChatAnnounceDisconnect(String nickName) {
        super(PacketType.ANNOUNCE_DISCONNECT);
        this.nicknameDisconnected = nickName;
    }

    public String getUID() {
        return uid;
    }

    public String getNickName() {
        return nicknameDisconnected;
    }
}
