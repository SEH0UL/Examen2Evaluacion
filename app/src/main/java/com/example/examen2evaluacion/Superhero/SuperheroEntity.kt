package com.example.examen2evaluacion.Superhero

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "superhero_table")
data class SuperheroEntity(
    @PrimaryKey val id: String,
    val name: String,
    val image: String
)