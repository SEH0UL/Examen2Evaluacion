package com.example.examen2evaluacion.Superhero

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SuperheroDao {
    @Query("SELECT * FROM superhero_table")
    suspend fun getAllSuperheroes(): List<SuperheroEntity>  // Devuelve List<SuperheroEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSuperheroes(superheroes: List<SuperheroEntity>)

    @Query("DELETE FROM superhero_table")
    suspend fun deleteAllSuperheroes()
}