package com.example.examen2evaluacion.Superhero

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SuperheroDetailDao {
    @Query("SELECT * FROM superhero_detail_table WHERE id = :id")
    suspend fun getSuperheroDetail(id: String): SuperheroDetailEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSuperheroDetail(detail: SuperheroDetailEntity)
}