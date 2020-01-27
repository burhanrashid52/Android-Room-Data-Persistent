package com.burhan.arch.room.models;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.burhan.arch.room.dao.UserDao;
import com.burhan.arch.room.dbutils.RoomDB;

import java.util.ArrayList;
import java.util.List;

import static com.burhan.arch.room.dbutils.Utils.getRandomDob;
import static com.burhan.arch.room.dbutils.Utils.getRandomString;
import static com.burhan.arch.room.dbutils.Utils.randBetween;

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

    public void insertDummyUsers() {
        List<User> usersList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            User user = new User();
            user.setFirstName(getRandomString(randBetween(6, 10)));
            user.setLastName(getRandomString(randBetween(6, 10)));
            user.setAge(randBetween(20, 80));
            user.setDateOfBirth(getRandomDob());
            usersList.add(user);
        }
        userDao.insertAll(usersList);
    }

   /* public LiveData<PagedList<User>> getAllUserPagination() {
        return userDao.byPagination().create(0,
                //  initial load position  0,
                new PagedList.Config.Builder()
                        .setEnablePlaceholders(true)
                        .setPageSize(10)
                        .setPrefetchDistance(5)
                        .build());
    }*/
}
