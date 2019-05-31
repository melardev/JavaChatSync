package com.melardev.chat.net.packets;

public class PacketAnnounce extends PacketChat {
    private final String announcement;

    public PacketAnnounce(String announcement) {
        super(PacketType.ANNOUNCE, "Server", "All");
        this.announcement = announcement;
    }

    public String getAnnouncement() {
        return announcement;
    }
}
