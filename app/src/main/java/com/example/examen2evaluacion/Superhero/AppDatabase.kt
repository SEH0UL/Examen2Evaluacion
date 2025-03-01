package com.example.examen2evaluacion.Superhero

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context

@Database(entities = [SuperheroEntity::class, SuperheroDetailEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun superheroDao(): SuperheroDao
    abstract fun superheroDetailDao(): SuperheroDetailDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "superhero_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}