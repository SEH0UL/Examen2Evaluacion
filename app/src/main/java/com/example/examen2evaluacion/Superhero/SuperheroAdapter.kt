package com.example.examen2evaluacion.Superhero

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.examen2evaluacion.R

class SuperheroAdapter(
    private val navigateToDetailActivity: (String) -> Unit
) : RecyclerView.Adapter<SuperheroViewHolder>() {

    private var superheroList: List<SuperheroItemResponse> = emptyList()

    fun updateList(list: List<SuperheroItemResponse>) {
        superheroList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuperheroViewHolder {
        return SuperheroViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_superhero, parent, false))
    }

    override fun onBindViewHolder(holder: SuperheroViewHolder, position: Int) {
        if (superheroList.isNotEmpty()) {
            holder.bind(superheroList[position], navigateToDetailActivity)
        }
    }

    override fun getItemCount() = superheroList.size
}
