package com.example.casino.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.casino.data.DAO.AccountDAO
import com.example.casino.data.entities.Account

@Database(entities = [Account::class], version = 1, exportSchema = false)
abstract class CasinoDatabase : RoomDatabase() {

    abstract fun accountDao(): AccountDAO

    companion object {
        @Volatile
        private var INSTANCE: CasinoDatabase? = null

        fun getDatabase(context: Context): CasinoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CasinoDatabase::class.java,
                    "casino_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
