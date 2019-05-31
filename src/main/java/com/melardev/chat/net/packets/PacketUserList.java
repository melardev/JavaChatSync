package com.melardev.chat.net.packets;
import java.util.Map;

public class PacketUserList extends PacketChat {
    private final Map<String, String> users;

    public PacketUserList(Map<String, String> users) {
        super(PacketType.UPDATE_CONTACTS);
        this.users = users;
    }

    public Map<String, String> getUsers() {
        return users;
    }
}
