package com.example.examen2evaluacion.Superhero

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.examen2evaluacion.databinding.ItemSuperheroBinding
import com.squareup.picasso.Picasso

class SuperheroViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemSuperheroBinding.bind(view)

    fun bind(superheroItemResponse: SuperheroItemResponse, navigateToDetailActivity: (String) -> Unit) {
        binding.tvSuperheroName.text = superheroItemResponse.name
        Picasso.get().load(superheroItemResponse.superheroImage.url).into(binding.ivSuperhero)
        binding.root.setOnClickListener {
            navigateToDetailActivity(superheroItemResponse.superheroId)
        }
    }
}
