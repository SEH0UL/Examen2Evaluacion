package com.example.examen2evaluacion

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.examen2evaluacion.Superhero.SuperheroListActivity

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        var SuperHeroApp = findViewById<Button>(R.id.SuperHeroApp)

        SuperHeroApp.setOnClickListener{ navigateToSuperheroListActivity() }

    }

    private fun navigateToSuperheroListActivity() {
        var intent = Intent (this, SuperheroListActivity::class.java)
        startActivity(intent)
    }

}