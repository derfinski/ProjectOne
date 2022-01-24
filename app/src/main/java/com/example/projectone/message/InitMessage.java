package com.example.projectone.message;

import com.example.projectone.game.GameManagerClient;

public class InitMessage extends RawMessage{
    public InitMessage(String sender, MessageTypes typ, String content) {
        super(sender, typ, content);
    }

    @Override
    public void handle() {
        GameManagerClient.id = Integer.parseInt(content);
    }

    @Override
    public String toString() {
        return "InitMessage{" +
                "ID='" + content + '\'' +
                '}';
    }
}
