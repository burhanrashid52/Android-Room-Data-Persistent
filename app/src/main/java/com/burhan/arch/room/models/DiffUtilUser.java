package com.burhan.arch.room.models;

import android.support.v7.util.DiffUtil;

import java.util.List;

/**
 * Created by Rashid on 10/4/2017.
 */

public class DiffUtilUser extends DiffUtil.Callback {

    private List<User> newUserList;
    private List<User> oldUserList;


    public DiffUtilUser(List<User> newUserList, List<User> oldUserList) {
        this.newUserList = newUserList;
        this.oldUserList = oldUserList;
    }

    @Override
    public int getOldListSize() {
        return oldUserList != null ? oldUserList.size() : 0;
    }

    @Override
    public int getNewListSize() {
        return newUserList != null ? newUserList.size() : 0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldUserList.get(oldItemPosition).getUid() == newUserList.get(newItemPosition).getUid();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldUserList.get(oldItemPosition).equals(newUserList.get(newItemPosition));
    }
}
