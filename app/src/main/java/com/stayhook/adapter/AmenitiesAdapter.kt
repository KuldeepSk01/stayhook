package com.stayhook.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.stayhook.R
import com.stayhook.databinding.ItemAmenitiesLayoutBinding
import com.stayhook.model.Amenities

class AmenitiesAdapter(val list: MutableList<Amenities>, val context: Context) :
    RecyclerView.Adapter<AmenitiesAdapter.AmenitiesAdapterVM>() {
    class AmenitiesAdapterVM(val b: ItemAmenitiesLayoutBinding) : ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AmenitiesAdapterVM {
        return AmenitiesAdapterVM(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_amenities_layout, parent, false
            )
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: AmenitiesAdapterVM, position: Int) {
        val images = list[position]
        holder.b.apply {
            ivItemAmenitiesCategory.setBackgroundResource(images.img!!)
            tvItemAmenitiesCategory.text = images.title
        }
    }
}