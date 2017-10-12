package com.burhan.arch.room.dao;

import android.arch.lifecycle.LiveData;
import android.arch.paging.LivePagedListProvider;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.burhan.arch.room.models.User;

import java.util.List;

//In user dao we define queries for entities
@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    LiveData<List<User>> getAll();

    @Query("SELECT * FROM user ORDER BY uid")
    public abstract LivePagedListProvider<Integer, User> byPagination();

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND "
            + "last_name LIKE :last LIMIT 1")
    User findByName(String first, String last);

    @Query("SELECT * FROM user WHERE uid = :userID")
    User findUserById(int userID);

    @Insert
    void insertAll(User... users);

    @Insert
    void insertAll(List<User> users);

    @Delete
    void delete(User user);

    @Update
    void updateUser(User user);
}