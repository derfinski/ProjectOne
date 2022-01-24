package com.example.projectone.message;

public abstract class RawMessage {
    String sender = null;
    MessageTypes typ = null;
    String content = null;
    public abstract void handle();
    public RawMessage(String sender, MessageTypes typ, String content){
        this.sender = sender;
        this.typ = typ;
        this.content = content;
    }

    @Override
    public String toString() {
        return "RawMessage{" +
                "sender='" + sender + '\'' +
                '}';
    }
}
