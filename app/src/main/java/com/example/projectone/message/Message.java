package com.example.projectone.message;

import android.util.Log;

import com.example.projectone.LogTags;

public class Message {
    private String stringRepresentation;
    private String sender;
    private MessageTypes typ;
    private String content;

    public static RawMessage parseMessage(String s){
        Log.i(LogTags.Network.name(), "Parsing: "+s);
        String[] splitMessage = s.split(":");
        String sender = splitMessage[0];
        String content = splitMessage[2];
        MessageTypes typ = MessageTypes.valueOf(splitMessage[1]);
        switch (typ){
            case INIT:return new InitMessage(sender, typ, content);
            case SUCCESS:break;
            case ERROR:break;
        }
        return null;
    }

    @Override
    public String toString() {
        return typ + " Message from "+sender + " : " +
                "{" + content + "}";
    }
}
