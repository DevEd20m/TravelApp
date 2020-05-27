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

class HomeAdapter(items: List<Places> = emptyList()) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    private var places: List<Places> by basicDiffUtil(
        emptyList(), { old, new -> old.id == new.id }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.ViewHolder {
        val view = parent.inflate(R.layout.item_places, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = places.size

    override fun onBindViewHolder(holder: HomeAdapter.ViewHolder, position: Int) {
        holder.bin(places[position])
    }

    class ViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        private val binding = ItemPlacesBinding.bind(view)

        fun bin(places: Places) {
            with(binding) {
                ImageViewBackground.load(places.pictureOne){
                    crossfade(true)
                }
                textViewNameMovie.text = places.department
            }
        }
    }

}