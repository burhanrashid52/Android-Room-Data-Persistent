package com.burhan.arch.room.dbutils;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.burhan.arch.room.dao.UserDao;
import com.burhan.arch.room.models.User;


//Here initialise daos with respective tables
@Database(entities = {User.class}, version = 3)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}