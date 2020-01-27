package com.burhan.arch.room.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Song(

        @PrimaryKey(autoGenerate = true)
        val id: Int,

        val name: String
)