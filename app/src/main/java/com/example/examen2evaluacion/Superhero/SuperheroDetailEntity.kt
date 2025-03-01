package com.example.examen2evaluacion.Superhero

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "superhero_detail_table")
data class SuperheroDetailEntity(
    // Entidad de Room que representa los detalles de un superhéroe en la base de datos local.
    @PrimaryKey val id: String,
    val name: String,  // Añadir este campo
    val image: String, // Añadir este campo
    val intelligence: String,
    val strength: String,
    val speed: String,
    val durability: String,
    val power: String,
    val combat: String,
    val fullName: String,
    val publisher: String
)