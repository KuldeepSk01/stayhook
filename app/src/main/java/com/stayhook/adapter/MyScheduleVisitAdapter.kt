package com.stayhook.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.stayhook.R
import com.stayhook.databinding.ItemMyScheduleLayoutBinding
import com.stayhook.model.response.TokenCollectedResponse

class MyScheduleVisitAdapter(
    val list: MutableList<TokenCollectedResponse>,
    val context: Context,
    private val listener: OnClickTokenListener
) :
    RecyclerView.Adapter<MyScheduleVisitAdapter.MyScheduleVisitVM>() {
    interface OnClickTokenListener {
        fun onClickToken(model: TokenCollectedResponse)
    }

    inner class MyScheduleVisitVM(val b: ItemMyScheduleLayoutBinding) : ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyScheduleVisitVM {
        return MyScheduleVisitVM(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_my_schedule_layout,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyScheduleVisitVM, position: Int) {
        val model = list[position]
        holder.b.apply {
            Glide.with(context).load(model.propertyImage).placeholder(R.drawable.default_image)
                .into(ivItemMyScheduleVisit)
            tvMyScheduleVisitApartmentType.text = model.propertyName
            tvMyScheduleVisitCurrentStatus.text = model.status
            tvMyScheduleVisitLocation.text = String.format(
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