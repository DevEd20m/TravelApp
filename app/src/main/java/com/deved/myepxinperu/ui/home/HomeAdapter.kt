package com.deved.myepxinperu.ui.home

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.deved.domain.Places
import com.deved.myepxinperu.R
import com.deved.myepxinperu.databinding.ItemPlacesBinding
import com.deved.myepxinperu.ui.common.basicDiffUtil
import com.deved.myepxinperu.ui.common.inflate

class HomeAdapter(private val listener:(Places)->Unit) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    var places: List<Places> by basicDiffUtil(
        emptyList(), { old, new -> old.name == new.name }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.ViewHolder {
        return ViewHolder(parent.inflate(R.layout.item_places, false))
    }

    override fun getItemCount(): Int = places.size

    override fun onBindViewHolder(holder: HomeAdapter.ViewHolder, position: Int) {
        val department = places[position]
        holder.bind(department)
        holder.itemView.setOnClickListener {
            listener.invoke(department)
        }
    }

    class ViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        private val binding = ItemPlacesBinding.bind(view)

        fun bind(place: Places) {
            with(binding) {
                textViewNameMovie.text = place.name
                imageViewBackground.load(place.picturesOne){
                    crossfade(true)
                    crossfade(500)
                }
            }
        }
    }

}