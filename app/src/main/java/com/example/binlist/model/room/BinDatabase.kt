package com.example.binlist.model.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.binlist.model.CardResponse

@Database(entities = [CardResponse::class], version = 1)
abstract class BinDatabase : RoomDatabase() {
    abstract fun binDao(): BinDAO

    companion object {
        private var INSTANCE: BinDatabase? = null

        fun getInstance(context: Context): BinDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        BinDatabase::class.java,
                        "card_responses"
                    ).fallbackToDestructiveMigration().build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}