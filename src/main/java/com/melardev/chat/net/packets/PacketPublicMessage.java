package com.melardev.chat.net.packets;

public class PacketPublicMessage extends PacketChat {
    private final String message;

    public PacketPublicMessage(String message) {
        super(PacketType.PUBLIC_MESSAGE);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
