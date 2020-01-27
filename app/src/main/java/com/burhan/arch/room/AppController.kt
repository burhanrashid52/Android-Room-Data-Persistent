package com.burhan.arch.room

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.burhan.arch.room.dbutils.SongDatabase

/**
 * Created by Burhanuddin on 9/9/2017.
 */
class AppController : Application() {

    override fun onCreate() {
        super.onCreate()

        instance = this

        songDb = Room.databaseBuilder(this, SongDatabase::class.java, "Sample.db")
                .createFromAsset("songs.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
    }

    companion object {
        lateinit var instance: AppController

        lateinit var songDb: SongDatabase

        @JvmStatic
        val context: Context
            get() = instance.applicationContext
    }
}