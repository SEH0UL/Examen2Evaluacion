package com.example.examen2evaluacion.Superhero

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.examen2evaluacion.R
import com.example.examen2evaluacion.databinding.ActivitySuperheroListBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SuperheroListActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_ID = "extra_id"
    }
    private lateinit var binding: ActivitySuperheroListBinding
    private lateinit var retrofit: Retrofit
    private lateinit var adapter: SuperheroAdapter
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_superhero_list)

        binding = ActivitySuperheroListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        retrofit = getRetrofit()
        database = AppDatabase.getDatabase(this)
        initUI()
    }

    private fun initUI() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchByName(query.orEmpty())
                return false
            }

            override fun onQueryTextChange(newText: String?) = false
        })
        adapter = SuperheroAdapter { superheroId -> navigateToDetail(superheroId) }
        binding.rvSuperhero.setHasFixedSize(true)
        binding.rvSuperhero.layoutManager = LinearLayoutManager(this)
        binding.rvSuperhero.adapter = adapter
    }

    private fun searchByName(query: String) {
        binding.progressBar.isVisible = true
        lifecycleScope.launch(Dispatchers.IO) {
            val myResponse: Response<SuperHeroDataResponse> =
                retrofit.create(ApiService::class.java).getSuperheroes(query)
            if (myResponse.isSuccessful) {
                Log.i("Consulta", "Funciona :)")
                val response: SuperHeroDataResponse? = myResponse.body()
                if (response != null) {
                    Log.i("Cuerpo de la consulta", response.toString())
                    // Convertir los datos de la API a entidades de Room
                    val superheroEntities = response.superheroes.map { superhero ->
                        SuperheroEntity(
                            id = superhero.superheroId,
                            name = superhero.name,
                            image = superhero.superheroImage.url
                        )
                    }
                    // Almacenar los datos en Room
                    database.superheroDao().deleteAllSuperheroes()
                    database.superheroDao().insertSuperheroes(superheroEntities)

                    // Obtener los datos de Room y pasarlos al Adapter
                    val superheroesFromDb = database.superheroDao().getAllSuperheroes()
                    runOnUiThread {
                        adapter.updateList(superheroesFromDb.map { superheroEntity ->
                            SuperheroItemResponse(
                                superheroId = superheroEntity.id,
                                name = superheroEntity.name,
                                superheroImage = SuperheroImageResponse(superheroEntity.image))
                        })
                        binding.progressBar.isVisible = false
                    }
                }
            } else {
                Log.i("Consulta", "No funciona :(")
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

    private fun navigateToDetail(id: String) {
        val intent = Intent(this, DetailSuperheroActivity::class.java)
        intent.putExtra(EXTRA_ID, id)
        startActivity(intent)
    }
}