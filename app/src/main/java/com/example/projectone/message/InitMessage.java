package com.example.projectone.message;

public class InitMessage extends RawMessage{
    public InitMessage(String sender, MessageTypes typ, String content) {
        super(sender, typ, content);
    }

    @Override
    public void handle() {

    }

    @Override
    public String toString() {
        return "InitMessage{" +
                "ID='" + content + '\'' +
                '}';
    }
}
