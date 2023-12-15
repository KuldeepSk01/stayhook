package com.stayhook.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.stayhook.R
import com.stayhook.databinding.ItemMyBookingLayoutBinding
import com.stayhook.model.response.TokenCollectedResponse

class MyBookingAdapter(val list: MutableList<TokenCollectedResponse>, val context: Context,private val listener:OnClickTokenListener) :
    RecyclerView.Adapter<MyBookingAdapter.MyBookingVM>() {
    interface OnClickTokenListener{
        fun onClickToken(model:TokenCollectedResponse)
    }

    inner class MyBookingVM(val b: ItemMyBookingLayoutBinding) : ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyBookingVM {
        return MyBookingVM(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_my_booking_layout,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyBookingVM, position: Int) {
        val model = list[position]
        holder.b.apply {
            Glide.with(context).load(model.propertyImage).placeholder(R.drawable.default_image).into(ivItemMyBooking)
            tvMyBookingApartmentType.text = model.propertyName
            tvMyBookingCurrentStatus.text = model.status
            tvMyBookingLocation.text = String.format(
                "%s%s%s%s%s%s",
                model.street,
                model.city,
                model.state,
                model.area,
                model.pincode,
                model.country
            )
            rlViewStatus.setOnClickListener {
                listener.onClickToken(model)
            }
        }
    }

}