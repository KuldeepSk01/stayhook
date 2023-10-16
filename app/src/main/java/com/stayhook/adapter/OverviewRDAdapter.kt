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
import com.stayhook.R
import com.stayhook.databinding.ItemOverviewRemmoendationDetailLayoutBinding
import com.stayhook.model.OverviewRD

class OverviewRDAdapter(val list: MutableList<OverviewRD>, val context: Context) :
    RecyclerView.Adapter<OverviewRDAdapter.OverviewVM>() {
    inner class OverviewVM(val b: ItemOverviewRemmoendationDetailLayoutBinding) : ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OverviewVM {
        val v = DataBindingUtil.inflate<ItemOverviewRemmoendationDetailLayoutBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_overview_remmoendation_detail_layout,
            parent,
            false
        )
        return OverviewVM(v)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: OverviewVM, position: Int) {
        val model = list[position]
        Glide.with(context).load(model.imageUrl).into(holder.b.ivRDOverviewImgItem)
    }
}