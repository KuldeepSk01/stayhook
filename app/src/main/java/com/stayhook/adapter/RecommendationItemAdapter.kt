package com.stayhook.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.stayhook.R
import com.stayhook.adapter.interfaces.RecommendationAdapterListener
import com.stayhook.databinding.ItemRecommendatationLayoutBinding
import com.stayhook.model.response.home.RecommendData
import com.stayhook.util.mLog

class RecommendationItemAdapter(
    private val list: MutableList<RecommendData>,
    private val context: Context,
    private val listener: RecommendationAdapterListener
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
        holder.b.apply {
            model.let {
                cbRItemFavoriteBtn.isChecked = it.is_wishlist != 0
                Glide.with(context).load(it.property_image).placeholder(R.drawable.default_image)
                    .into(ivRItem)
                tvRItemName.text = it.property_type
                tvRItemApartment.text = it.property_name
                mLog(
                    "Reccommendation item Loction ${
                        String.format(
                            "%s%s%s%s%s%s",
                            it.street,
                            it.city,
                            it.state,
                            it.area,
                            it.pincode,
                            it.country
                        )
                    } "
                )
                tvRItemLocation.text = String.format(
                    "%s%s%s%s%s%s",
                    it.street,
                    it.city,
                    it.state,
                    it.area,
                    it.pincode,
                    it.country
                )
                tvRItemCost.text = String.format(
                    "%s %d",
                    context.getString(R.string.indian_currency_symbol),
                    it.price
                )
                tvRatingRItem.text = it.rating.toString()
            }
            ivRItem.setOnClickListener {
                listener.onClickItems(model)
            }
            cbRItemFavoriteBtn.setOnClickListener {
                listener.addFavoriteItem(model)
                notifyChange()
            }
        }

    }


}