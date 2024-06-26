package com.stayhook.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.stayhook.R
import com.stayhook.databinding.ItemAmenitiesLayoutBinding
import com.stayhook.model.response.getpopertydetail.PropertyInventory

class AmenitiesAdapter(val list: MutableList<PropertyInventory>, val context: Context) :
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
        val model = list[position]
        holder.b.apply {
            Glide.with(context).load(model.image).placeholder(R.drawable.ac).into(ivItemAmenitiesCategory)
            tvItemAmenitiesCategory.text = model.inventory_sub_category
        }
    }
}