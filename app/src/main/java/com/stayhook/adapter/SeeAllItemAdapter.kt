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
import com.stayhook.databinding.ItemSeeAllLayoutBinding
import com.stayhook.model.response.home.RecommendData

class SeeAllItemAdapter(
    val list: MutableList<RecommendData>,
    val context: Context,
    private val listener: OnItemsClickListener
) : RecyclerView.Adapter<SeeAllItemAdapter.SeeAllItemVM>() {
    inner class SeeAllItemVM(val b: ItemSeeAllLayoutBinding) : ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeeAllItemVM {
        return SeeAllItemVM(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_see_all_layout,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SeeAllItemVM, position: Int) {
        val model = list[position]
        holder.b.apply {
            model.let {
                Glide.with(context).load(it.property_image).placeholder(R.drawable.default_image)
                    .into(ivSeeAllItem)
                tvSealAllItemTitle.text = it.property_name
                tvSeeAllItemLocation.text = String.format(
                    "%s%s%s%s%s%s",
                    it.street,
                    it.city,
                    it.state,
                    it.area,
                    it.pincode,
                    it.country
                )
                tvSeeAllItemCost.text = String.format(
                    "%s %d",
                    context.getString(R.string.indian_currency_symbol),
                    it.price
                )
                tvSeeAllItemPerGender.text = it.gender_type

            }
            cvSeeAllItem.setOnClickListener {
                listener.onCLickItems(model)
            }

        }
    }

}