package com.burhan.arch.room.models;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.burhan.arch.room.dao.UserDao;
import com.burhan.arch.room.dbutils.RoomDB;

import java.util.List;

/**
 * Created by Burhanuddin on 9/10/2017.
 */

public class UserModel extends AndroidViewModel {

    private final UserDao userDao;

    public UserModel(Application application) {
        super(application);
        userDao = RoomDB.getDefaultInstance().userDao();
    }

    public LiveData<List<User>> getAllUser() {
        return userDao.getAll();
    }
}
