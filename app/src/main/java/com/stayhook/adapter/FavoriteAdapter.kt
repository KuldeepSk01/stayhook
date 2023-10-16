package com.stayhook.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.stayhook.R
import com.stayhook.databinding.ItemFavoriteLayoutBinding
import com.stayhook.model.Recommendation

class FavoriteAdapter(val list: MutableList<Recommendation>, val context: Context) :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteVM>() {
    inner class FavoriteVM(val b: ItemFavoriteLayoutBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteVM {
        val v = DataBindingUtil.inflate<ItemFavoriteLayoutBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_favorite_layout, parent, false
        )
        return FavoriteVM(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: FavoriteVM, position: Int) {
        val model = list[position]
        holder.b.apply {
            tvRAItemTitle.text = model.name
            tvRAItemLocation.text = model.location
            tvRAItemApartmentType.text = model.apartmentType
            tvRAItemCost.text = "$ ${model.price}"
            tvRARatingItem.text = model.rating
            Glide.with(context).load(model.imgUrl).into(ivRAItem)

        }

    }

}
