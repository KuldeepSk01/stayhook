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
import com.stayhook.databinding.ItemNearbyLocationLayoutBinding
import com.stayhook.model.Recommendation

class NearbyLocationItemAdapter(val list: MutableList<Recommendation>, val context: Context) :
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
        holder.b.tvItemNearbyLocation.text = model.location
        Glide.with(context).load(model.imgUrl).into(object : CustomTarget<Drawable>() {
            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                holder.b.ivItemNearbyLocation.background = resource
            }

            override fun onLoadCleared(placeholder: Drawable?) {
            }

        })
    }

}