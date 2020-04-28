package com.hazyaz.temps.Message;

public class Message {
    public String number;
    public String message;
    public Long date;
    public String type;
    public Message(String number, String message, Long date, String type) {
        this.number = number;
        this.message = message;
        this.date = date;
        this.type = type;
    }


    public Message() {
    }
}
