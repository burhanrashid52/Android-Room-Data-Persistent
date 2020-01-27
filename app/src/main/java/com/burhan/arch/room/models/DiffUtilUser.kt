package com.burhan.arch.room.models

import androidx.recyclerview.widget.DiffUtil

/**
 * Created by Rashid on 10/4/2017.
 */
class DiffUtilUser(private val newUserList: List<User>, private val oldUserList: List<User>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldUserList.size
    }

    override fun getNewListSize(): Int {
        return newUserList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldUserList[oldItemPosition].uid == newUserList[newItemPosition].uid
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldUserList[oldItemPosition].equals(newUserList[newItemPosition])
    }

}