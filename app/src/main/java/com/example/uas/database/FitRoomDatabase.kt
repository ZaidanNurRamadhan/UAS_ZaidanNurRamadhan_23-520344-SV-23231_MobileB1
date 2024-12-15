package com.example.uas.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FitEntity::class], version = 2, exportSchema = false)
abstract class FitRoomDatabase : RoomDatabase() {
    abstract fun fitDao(): FitDao?

    companion object {
        @Volatile
        private var INSTANCE: FitRoomDatabase? = null

        fun getDatabase(context: Context): FitRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(FitRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        FitRoomDatabase::class.java,
                        "photo_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}
