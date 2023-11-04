package com.stayhook.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.stayhook.R
import com.stayhook.databinding.ItemImagesLayoutBinding
import com.stayhook.model.response.getpopertydetail.PropertyImage

class RecommendationDetailAdapter(private val list: MutableList<PropertyImage>, val context: Context) :
    RecyclerView.Adapter<RecommendationDetailAdapter.OnVM>() {
    inner class OnVM(val b: ItemImagesLayoutBinding) : ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnVM {
        val view = DataBindingUtil.inflate<ItemImagesLayoutBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_images_layout, parent, false
        )

        return OnVM(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: OnVM, position: Int) {
        val model = list[position]
        Glide.with(context).load(model.image).into(holder.b.ivOnboardingItem)
    }

}