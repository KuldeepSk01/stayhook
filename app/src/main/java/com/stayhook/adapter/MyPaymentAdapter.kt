package com.stayhook.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stayhook.R
import com.stayhook.adapter.interfaces.OnPaymentListener
import com.stayhook.databinding.ItemMyPaymentLayoutBinding
import com.stayhook.model.response.MyPaymentsResponse
import com.stayhook.util.setMonthYearDateFormat

class MyPaymentAdapter(val list: MutableList<MyPaymentsResponse>, val context : Context,private val listener:OnPaymentListener):RecyclerView.Adapter<MyPaymentAdapter.MyPaymentVM>() {
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
           // tvItemRoomType.text = "1 Room"
            tvApartmentItemMyPayment.text = model.propertyName
            tvMovedStatusPayment.text = String.format("%s %s","Rent for ",setMonthYearDateFormat(model.month))
            tvLocationItemPayment.text = String.format("%s%s%s%s%s%s",model.area,model.street,model.city,model.state,model.country,model.pinCode)
            amountTV.text = String.format("%s%s",context.getString(R.string.indian_currency_symbol),model.amount)
            Glide.with(context).load(model.propertyImage).placeholder(R.drawable.default_image).into(ivitemPaymentImg)
            when (model.status){
                context.getString(R.string.pending)->{
                    tvPaymentPriceBtn.text= String.format("%s %s%s","Pay",context.getString(R.string.indian_currency_symbol),model.amount)
                }
                context.getString(R.string.paid)->{
                    tvPaymentPriceBtn.apply {
                        tvMovedStatusPayment.visibility = View.GONE
                        clPaymentAmountItem.visibility = View.GONE
                        text = String.format("%s%s %s %s",context.getString(R.string.indian_currency_symbol),model.amount.toString(),"Rent Paid for",
                            setMonthYearDateFormat(model.month)
                        )
                        background = ContextCompat.getDrawable(context,R.drawable.otp_box_background)
                        setTextColor(context.getColor(R.color.text_hint_color))
                    }
                }
            }

            tvPaymentPriceBtn.setOnClickListener {
                if (model.status==context.getString(R.string.pending)){
                    listener.onPayClickListener(model)
                }
            }

        }
    }
}