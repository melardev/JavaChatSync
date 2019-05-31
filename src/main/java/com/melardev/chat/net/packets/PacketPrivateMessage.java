package com.melardev.chat.net.packets;

public class PacketPrivateMessage extends PacketChat {
    private final String message;

    public PacketPrivateMessage(String from, String destination, String message) {
        super(PacketType.PRIVATE_MESSAGE, from, destination);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
