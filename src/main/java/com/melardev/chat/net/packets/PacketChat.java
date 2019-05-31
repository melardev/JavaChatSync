package com.melardev.chat.net.packets;

import java.io.Serializable;

public abstract class PacketChat implements Serializable {

    private PacketType packetType;
    private String from;
    private String destination;

    public PacketChat(PacketType packetType) {
        this.packetType = packetType;
    }

    public PacketChat(PacketType packetType, String destination) {
        this.packetType = packetType;
        this.destination = destination;
    }

    public PacketChat(PacketType packetType, String from, String destination) {
        this.packetType = packetType;
        this.from = from;
        this.destination = destination;
    }

    public PacketType getPacketType() {
        return packetType;
    }

    public void setPacketType(PacketType packetType) {
        this.packetType = packetType;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void swap() {
        String tempFrom = from;
        from = destination;
        destination = tempFrom;
    }

}
