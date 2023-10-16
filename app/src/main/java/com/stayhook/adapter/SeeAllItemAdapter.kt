package com.stayhook.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.stayhook.R
import com.stayhook.databinding.ItemSeeAllLayoutBinding
import com.stayhook.model.Recommendation

class SeeAllItemAdapter(val list: MutableList<Recommendation>,val context : Context):RecyclerView.Adapter<SeeAllItemAdapter.SeeAllItemVM>() {
inner class SeeAllItemVM(val b : ItemSeeAllLayoutBinding):ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeeAllItemVM {
        return SeeAllItemVM(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.item_see_all_layout,parent,false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SeeAllItemVM, position: Int) {
        val model = list[position]
        holder.b.apply {
            Glide.with(context).load(model.imgUrl).into(ivSeeAllItem)
            tvSealAllItemTitle.text =model.name
            tvSeeAllItemLocation.text = model.location
            tvSeeAllItemCost.text = "$${model.price}"
            tvSeeAllItemPerGender.text = model.gender
        }
    }

}