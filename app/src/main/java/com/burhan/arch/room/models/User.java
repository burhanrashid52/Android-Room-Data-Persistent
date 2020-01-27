package com.burhan.arch.room.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import android.graphics.Bitmap;

import java.util.Date;

//This is model entity we define for a table i.e User table here
@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "first_name")
    private String firstName;

    @ColumnInfo(name = "last_name")
    private String lastName;

    private int age;

    private Date dateOfBirth;

    private Bitmap userBitmapImage;

    public User() {
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Bitmap getUserBitmapImage() {
        return userBitmapImage;
    }

    public void setUserBitmapImage(Bitmap userBitmapImage) {
        this.userBitmapImage = userBitmapImage;
    }

    public boolean equals(User obj) {
        return uid == obj.getUid()
                && firstName.equals(obj.getFirstName())
                && lastName.equals(obj.getLastName())
                && age == obj.getAge()
                && dateOfBirth.getTime() == obj.getDateOfBirth().getTime();
    }
}