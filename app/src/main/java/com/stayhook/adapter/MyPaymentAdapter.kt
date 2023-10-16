package com.stayhook.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.stayhook.R
import com.stayhook.databinding.ItemHelpCenterQuestionLayoutBinding
import com.stayhook.databinding.ItemMyPaymentLayoutBinding
import com.stayhook.model.Recommendation

class MyPaymentAdapter(val list: MutableList<Recommendation>,val context : Context):RecyclerView.Adapter<MyPaymentAdapter.MyPaymentVM>() {
    inner class MyPaymentVM(val b: ItemMyPaymentLayoutBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPaymentVM {
        return MyPaymentVM(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_my_payment_layout,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyPaymentVM, position: Int) {
        val model = list[position]
        holder.b.apply {
            tvItemRoomType.text = "1 Room"
            tvApartmentItemMyPayment.text = model.name
            tvLocationItemPayment.text = model.location
            tvPaymentPriceBtn.text=model.price
            amountTV.text = "$${model.price}"
        }
    }
}