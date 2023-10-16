package com.stayhook.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.stayhook.R
import com.stayhook.databinding.ItemRecentlyAddedLayoutBinding
import com.stayhook.model.Recommendation

class RecentlyAddedItemAdapter(val list: MutableList<Recommendation>, val context: Context) :
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
            tvRAItemTitle.text = model.name
            tvRAItemLocation.text = model.location
            tvRAItemApartmentType.text = model.apartmentType
            tvRAItemCost.text = "$ ${model.price}"
            tvRARatingItem.text = model.rating
        }
        Glide.with(context).load(model.imgUrl).into(object : CustomTarget<Drawable>() {
            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                holder.b.ivRAItem.background = resource
            }

            override fun onLoadCleared(placeholder: Drawable?) {
            }

        })
    }

}