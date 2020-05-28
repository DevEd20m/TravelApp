package com.deved.myepxinperu.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.deved.domain.Places
import com.deved.myepxinperu.R
import com.deved.myepxinperu.databinding.ItemPlacesBinding
import com.deved.myepxinperu.ui.common.basicDiffUtil

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    var places: List<Places> by basicDiffUtil(
        emptyList(), { old, new -> old.department == new.department }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_places, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = places.size

    override fun onBindViewHolder(holder: HomeAdapter.ViewHolder, position: Int) {
        holder.bind(places[position])
    }

    class ViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        private val binding = ItemPlacesBinding.bind(view)

        fun bind(places: Places) {
            with(binding) {
                imageViewBackground.load(places.pictureOne){
                    crossfade(true)
                    crossfade(500)
                }
                textViewNameMovie.text = places.description
            }
        }
    }

}