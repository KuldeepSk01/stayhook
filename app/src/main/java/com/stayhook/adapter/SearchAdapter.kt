package com.stayhook.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stayhook.R
import com.stayhook.databinding.ItemSearchFragmentLayoutBinding
import com.stayhook.model.Recommendation

class SearchAdapter(
    val list: MutableList<Recommendation>,
    val context: Context,
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
            Glide.with(context).load(model.imgUrl).into(ivItemSearch)
            tvSearchItemTitle.text = model.name
            tvSearchApartmentType.text = model.apartmentType
            tvSearchLocation.text = model.location
            tvCostItemSearch.text = "$${model.price}"

        }
    }

}
