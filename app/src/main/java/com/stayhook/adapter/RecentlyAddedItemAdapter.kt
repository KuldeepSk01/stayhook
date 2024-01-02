package com.stayhook.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.stayhook.R
import com.stayhook.adapter.interfaces.RecommendationAdapterListener
import com.stayhook.databinding.ItemRecentlyAddedLayoutBinding
import com.stayhook.model.response.home.RecommendData

class RecentlyAddedItemAdapter(
    val list: MutableList<RecommendData>,
    val context: Context,
    private val listener: RecommendationAdapterListener
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
            model.let {
                Glide.with(context).load(it.property_image).placeholder(R.drawable.default_image)
                    .into(ivRAItem)
                tvRAItemTitle.text = it.property_type
                tvRAItemLocation.text = String.format(
                    "%s%s%s%s%s%s",
                    it.street,
                    it.city,
                    it.state,
                    it.area,
                    it.pincode,
                    it.country
                )
                tvRAItemApartmentType.text = it.property_name
                tvRAItemCost.text = String.format(
                    "%s %d",
                    context.getString(R.string.indian_currency_symbol),
                    it.price
                )
                tvRARatingItem.text = it.rating.toString()

            }
            clItemRecentAdded.setOnClickListener {
                listener.onClickItems(model)
            }
        }
    }

}