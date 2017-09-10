package com.burhan.arch.room.dbutils;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.burhan.arch.room.AppController;


/**
 * Created by Burhanuddin on 9/9/2017.
 */
//This is singleton class define to get Database throughout the app
public class RoomDB {
    private static final String DATABASE_NAME = "user-database";
    private static RoomDB ourInstance;
    private AppDatabase appDatabase;

    public static AppDatabase getDefaultInstance() {
        if (ourInstance == null) {
            ourInstance = new RoomDB();
        }
        return ourInstance.getAppDatabase();
    }

    private RoomDB() {
        Context context = AppController.getContext();
        //Intiliaze the room database with database name
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }

    private AppDatabase getAppDatabase() {
        return appDatabase;
    }
}
