package com.burhan.arch.room.dbutils

import androidx.room.Database
import androidx.room.RoomDatabase
import com.burhan.arch.room.dao.SongsDao
import com.burhan.arch.room.models.Song

@Database(entities = [Song::class], version = 1, exportSchema = false)
abstract class SongDatabase : RoomDatabase() {
    abstract fun songsDao(): SongsDao
}