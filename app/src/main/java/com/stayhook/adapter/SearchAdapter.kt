package com.stayhook.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stayhook.R
import com.stayhook.adapter.interfaces.OnItemsClickListener
import com.stayhook.databinding.ItemSearchFragmentLayoutBinding
import com.stayhook.model.response.home.RecommendData

class SearchAdapter(
    val list: MutableList<RecommendData>,
    val context: Context,
    val listener: OnItemsClickListener
) : RecyclerView.Adapter<SearchAdapter.SearchVM>() {
    inner class SearchVM(val b: ItemSearchFragmentLayoutBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchVM {
        return SearchVM(
            DataBindingUtil.inflate<ItemSearchFragmentLayoutBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_search_fragment_layout,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SearchVM, position: Int) {
        val model = list[position]
        holder.b.apply {
            model.let {
                Glide.with(context).load(it.property_image).placeholder(R.drawable.default_image)
                    .into(ivItemSearch)
                tvSearchItemTitle.text = it.property_type
                tvSearchApartmentType.text = it.property_name
                tvSearchLocation.text = String.format(
                    "%s%s%s%s%s%s",
                    it.street,
                    it.city,
                    it.state,
                    it.area,
                    it.pincode,
                    it.country
                )
                tvRatingItemSearch.text = it.rating.toString()
                tvCostItemSearch.text = String.format(
                    "%s %d",
                    context.getString(R.string.indian_currency_symbol),
                    it.price
                )
                clItemSearchFl.setOnClickListener {
                    listener.onCLickItems(model)
                }

            }
        }
    }

}
