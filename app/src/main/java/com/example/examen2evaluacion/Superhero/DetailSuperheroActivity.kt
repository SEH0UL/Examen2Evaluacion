package com.example.examen2evaluacion.Superhero

import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.examen2evaluacion.R
import com.example.examen2evaluacion.Superhero.SuperheroListActivity.Companion.EXTRA_ID
import com.example.examen2evaluacion.databinding.ActivityDetailSuperheroBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.roundToInt

class DetailSuperheroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailSuperheroBinding
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailSuperheroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = AppDatabase.getDatabase(this)
        val id: String = intent.getStringExtra(EXTRA_ID).orEmpty()
        getSuperheroInformation(id)
    }

    private fun getSuperheroInformation(id: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            val superheroDetail = database.superheroDetailDao().getSuperheroDetail(id)
            if (superheroDetail != null) {
                runOnUiThread { createUI(superheroDetail) }
            } else {
                // Si no hay datos en Room, obtenerlos de la API
                val response = getRetrofit().create(ApiService::class.java).getSuperheroDetail(id)
                if (response.body() != null) {
                    val superhero = response.body()!!
                    // Convertir los datos de la API a una entidad de Room
                    val superheroDetailEntity = SuperheroDetailEntity(
                        id = id,
                        name = superhero.name,  // Añadir el nombre
                        image = superhero.image.url,  // Añadir la imagen
                        intelligence = superhero.powerstats.intelligence,
                        strength = superhero.powerstats.strength,
                        speed = superhero.powerstats.speed,
                        durability = superhero.powerstats.durability,
                        power = superhero.powerstats.power,
                        combat = superhero.powerstats.combat,
                        fullName = superhero.biography.fullName,
                        publisher = superhero.biography.publisher
                    )
                    // Almacenar los datos en Room
                    database.superheroDetailDao().insertSuperheroDetail(superheroDetailEntity)
                    runOnUiThread { createUI(superheroDetailEntity) }
                }
            }
        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://superheroapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun createUI(superhero: SuperheroDetailEntity) {
        Picasso.get().load(superhero.image).into(binding.ivSuperhero)
        binding.tvSuperheroName.text = superhero.name
        binding.tvSuperheroRealName.text = superhero.fullName
        binding.tvPublisher.text = superhero.publisher

        // Llamar a prepareStats para actualizar las vistas de las estadísticas
        prepareStats(superhero)
    }

    private fun prepareStats(superhero: SuperheroDetailEntity) {
        updateHeight(binding.viewIntelligence, superhero.intelligence)
        updateHeight(binding.viewStrength, superhero.strength)
        updateHeight(binding.viewSpeed, superhero.speed)
        updateHeight(binding.viewDurability, superhero.durability)
        updateHeight(binding.viewPower, superhero.power)
        updateHeight(binding.viewCombat, superhero.combat)
    }

    private fun updateHeight(view: View, stat: String) {
        val params = view.layoutParams
        val statValue = stat.toIntOrNull() ?: 0 // Convertir a Int o usar 0 si no es un número válido
        params.height = pxToDp(statValue.toFloat())
        view.layoutParams = params
    }

    private fun pxToDp(px: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, resources.displayMetrics).roundToInt()
    }
}