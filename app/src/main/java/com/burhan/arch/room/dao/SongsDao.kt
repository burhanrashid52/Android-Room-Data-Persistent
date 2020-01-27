package com.burhan.arch.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.burhan.arch.room.models.Song

@Dao
interface SongsDao {

    @get:Query("SELECT * FROM Song")
    val songs: List<Song>

}