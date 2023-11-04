package com.stayhook.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.stayhook.R
import com.stayhook.adapter.interfaces.OnItemsClickListener
import com.stayhook.databinding.ItemNearbyLocationLayoutBinding
import com.stayhook.model.response.home.RecommendData

class NearbyLocationItemAdapter(
    val list: MutableList<RecommendData>,
    val context: Context,
    private val listener: OnItemsClickListener
) :
    RecyclerView.Adapter<NearbyLocationItemAdapter.NearbyLVM>() {
    inner class NearbyLVM(val b: ItemNearbyLocationLayoutBinding) : ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NearbyLVM {
        val v = DataBindingUtil.inflate<ItemNearbyLocationLayoutBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_nearby_location_layout, parent, false
        )
        return NearbyLVM(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: NearbyLVM, position: Int) {
        val model = list[position]
        holder.b.apply {
            holder.b.tvItemNearbyLocation.text = "Noida Sec-4"
            Glide.with(context).load(model.property_image).into(ivItemNearbyLocation)
            rlItemSearchNearByLocation.setOnClickListener {
                listener.onCLickItems(model)
            }
        }
    }

}