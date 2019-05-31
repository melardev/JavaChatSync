package com.melardev.chat.net.packets;

public class PacketLoginRequest extends PacketChat {
    private String password;
    private String username;

    public PacketLoginRequest(String username, String password) {
        super(PacketType.LOGIN_REQUEST);
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
