package com.example.projectone;

public class Message {
    private String stringRepresentation;
    private Message(String stringRepresentation){
        this.stringRepresentation = stringRepresentation;
    }
    public static Message parseMessage(String s){
        String[] splitMessage = s.split(":");
        String sender = splitMessage[0];
        MessageTypes typ = MessageTypes.valueOf(splitMessage[1]);
        switch (typ){
            case SUCCESS:break;
            case ERROR:break;
        }
        return new Message(s);
    }
}
