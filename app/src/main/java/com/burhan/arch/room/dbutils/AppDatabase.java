package com.burhan.arch.room.dbutils;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.burhan.arch.room.dao.UserDao;
import com.burhan.arch.room.models.User;


//Here initialise daos with respective tables
@Database(entities = {User.class}, version = 3, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}