package com.hazyaz.temps.Message;

import java.util.Date;

public class Message {
    public String number;
    public String message;
    public Date date;
    public String type;
    public Message(String number, String message, Date date, String type) {
        this.number = number;
        this.message = message;
        this.date = date;
        this.type = type;
    }


    public Message() {
    }
}
