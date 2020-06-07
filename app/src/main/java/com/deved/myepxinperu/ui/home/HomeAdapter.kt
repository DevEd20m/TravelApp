package com.deved.myepxinperu.ui.home

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.deved.domain.Department
import com.deved.myepxinperu.R
import com.deved.myepxinperu.databinding.ItemPlacesBinding
import com.deved.myepxinperu.ui.common.basicDiffUtil
import com.deved.myepxinperu.ui.common.inflate

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    var places: List<Department> by basicDiffUtil(
        emptyList(), { old, new -> old.name == new.name }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.ViewHolder {
        return ViewHolder(parent.inflate(R.layout.item_places, false))
    }

    override fun getItemCount(): Int = places.size

    override fun onBindViewHolder(holder: HomeAdapter.ViewHolder, position: Int) {
        holder.bind(places[position])
    }

    class ViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        private val binding = ItemPlacesBinding.bind(view)

        fun bind(department: Department) {
            with(binding) {
                textViewNameMovie.text = department.place?.name
                imageViewBackground.load(department.place?.picturesOne){
                    crossfade(true)
                    crossfade(500)
                }
            }
        }
    }

}