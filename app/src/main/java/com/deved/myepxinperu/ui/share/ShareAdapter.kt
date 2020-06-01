package com.deved.myepxinperu.ui.share

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.deved.myepxinperu.R
import com.deved.myepxinperu.databinding.ItemPictureBinding
import com.deved.myepxinperu.ui.common.inflate
import com.deved.myepxinperu.ui.model.Picture

class ShareAdapter : RecyclerView.Adapter<ShareAdapter.ViewHolder>() {

    var pictures: MutableList<Picture> = mutableListOf()
    fun setData(list:MutableList<Picture>){
        this.pictures = list
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShareAdapter.ViewHolder {
        return ViewHolder(parent.inflate(R.layout.item_picture, false))
    }

    override fun getItemCount(): Int = pictures.size

    override fun onBindViewHolder(holder: ShareAdapter.ViewHolder, position: Int) {
        holder.bind(pictures[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemPictureBinding.bind(itemView)

        fun bind(picture: Picture) {
            with(binding) {
                imageViewPicture.load(picture.picture) {
                    crossfade(true)
                    crossfade(500)
                }
                textViewNameTourist.text = picture.name
            }
        }
    }
}