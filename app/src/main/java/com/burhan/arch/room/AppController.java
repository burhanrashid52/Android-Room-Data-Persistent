package com.burhan.arch.room;

import android.app.Application;
import android.content.Context;

/**
 * Created by Burhanuddin on 9/9/2017.
 */

public class AppController extends Application {

    private static AppController appController;

    @Override
    public void onCreate() {
        super.onCreate();
        appController = this;
    }


    public static AppController getInstance() {
        return appController;
    }

    public static Context getContext() {
        return appController.getApplicationContext();
    }
}
