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
import com.stayhook.databinding.ItemRecentlyAddedLayoutBinding
import com.stayhook.model.response.home.RecommendData

class RecentlyAddedItemAdapter(
    val list: MutableList<RecommendData>,
    val context: Context,
    private val listener: OnItemsClickListener
) :
    RecyclerView.Adapter<RecentlyAddedItemAdapter.NearbyLVM>() {
    inner class NearbyLVM(val b: ItemRecentlyAddedLayoutBinding) : ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NearbyLVM {
        val v = DataBindingUtil.inflate<ItemRecentlyAddedLayoutBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_recently_added_layout, parent, false
        )
        return NearbyLVM(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: NearbyLVM, position: Int) {
        val model = list[position]
        holder.b.apply {
            Glide.with(context).load(model.property_image).into(ivRAItem)
            tvRAItemTitle.text = model.property_type
            tvRAItemLocation.text = "Secton-64"
            tvRAItemApartmentType.text = model.property_name
            tvRAItemCost.text = "$${model.price}"
            tvRARatingItem.text = model.rating.toString()

            clItemRecentAdded.setOnClickListener {
                listener.onCLickItems(model)
            }
        }
    }

}