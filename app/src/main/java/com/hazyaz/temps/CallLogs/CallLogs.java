package com.hazyaz.temps.CallLogs;

public class CallLogs {
    public String number;
    public String callType;
    public  int duration;
    public String name;
    public Long date;

    public CallLogs(String number, String callType, int duration, String name, Long date) {
        this.number = number;
        this.callType = callType;
        this.duration = duration;
        this.name = name;
        this.date = date;
    }

    public CallLogs() {

    }
}
