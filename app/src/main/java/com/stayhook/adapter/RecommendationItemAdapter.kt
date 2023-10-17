package com.stayhook.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.stayhook.R
import com.stayhook.adapter.interfaces.OnItemsClickListener
import com.stayhook.databinding.ItemRecommendatationLayoutBinding
import com.stayhook.model.Recommendation

class RecommendationItemAdapter(
    private val list: MutableList<Recommendation>,
    private val context: Context,
    private val listener :OnItemsClickListener
) : RecyclerView.Adapter<RecommendationItemAdapter.RItemVm>() {

    inner class RItemVm(val b: ItemRecommendatationLayoutBinding) : ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RItemVm {
        val v = DataBindingUtil.inflate<ItemRecommendatationLayoutBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_recommendatation_layout, parent, false
        )
        /*

          val v = ItemRecommendatationLayoutBinding.inflate(
              LayoutInflater.from(parent.context),
              parent,
              false
          )*/
        return RItemVm(v)
    }

    override fun getItemCount() = list.size
    override fun onBindViewHolder(holder: RItemVm, position: Int) {
        val model = list[position]
        Log.d("TAG", "onBindViewHolder: ${model.imgUrl}")
        Glide.with(context).load(model.imgUrl).into(object : SimpleTarget<Drawable>() {
            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                holder.b.ivRItem.background=resource
            }
        })
        holder.b.tvRItemName.text = model.name
        holder.b.tvRItemApartment.text = model.apartmentType
        holder.b.tvRItemCost.text = "$ ${model.price}"
        holder.b.tvRatingRItem.text = model.rating
        holder.b.cvRItem.setOnClickListener {
            listener.onCLickItems(model)
        }
    }

}