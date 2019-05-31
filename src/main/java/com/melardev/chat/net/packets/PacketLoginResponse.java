package com.melardev.chat.net.packets;

public class PacketLoginResponse extends PacketChat {
    private boolean success;
    private String assignedUUID;
    private String loginMessage;

    public PacketLoginResponse(boolean success, String assignedUUID, String loginMessage) {
        super(PacketType.LOGIN_RESPONSE);
        this.success = success;
        this.assignedUUID = assignedUUID;
        this.loginMessage = loginMessage;
    }


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getAssignedUUID() {
        return assignedUUID;
    }

    public void setAssignedUUID(String assignedUUID) {
        this.assignedUUID = assignedUUID;
    }

    public String getLoginMessage() {
        return loginMessage;
    }
}
