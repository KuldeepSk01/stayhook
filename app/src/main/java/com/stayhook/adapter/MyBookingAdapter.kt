package com.stayhook.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.stayhook.R
import com.stayhook.databinding.ItemMyBookingLayoutBinding
import com.stayhook.model.response.MyBookingResponse
import com.stayhook.util.mToast
import com.stayhook.util.setDateFormat
import com.stayhook.util.setMonthYearDateFormat

class MyBookingAdapter(
    val list: MutableList<MyBookingResponse>,
    val context: Context,
    private val listener: OnMyBookingListener
) :
    RecyclerView.Adapter<MyBookingAdapter.MyBookingVM>() {
    interface OnMyBookingListener {
        fun onMoveOutBooking(model: MyBookingResponse)
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
            Glide.with(context).load(model.propertyImage).placeholder(R.drawable.default_image)
                .into(ivItemMyBooking)
            tvMyBookingApartmentType.text = model.propertyName
            tvMyBookingLocation.text = String.format("%s", model.area)

            if (model.moveinFlag=="1"){
                tvMyBookingCurrentStatus.text = String.format("%s %s",context.getString(R.string.moved_in_text),
                    setDateFormat(model.moveinDate)
                )
            }
            if (model.moveOutReqFlag=="1"){
                tvMyBookingReqMoveOutStatus.visibility = View.VISIBLE
                tvMyBookingReqMoveOutStatus.text = String.format("%s %s",context.getString(R.string.move_out_request),
                    setDateFormat(model.moveOutReqDate)
                )
            } else{
                tvMyBookingReqMoveOutStatus.visibility = View.GONE
            }

           /* tvMyBookingLocation.text = String.format(
                "%s%s%s%s%s%s",
                model.street,
                model.city,
                model.state,
                model.area,
                model.pinCode,
                model.country
            )*/

            if (model.moveoutFlag=="1"){
                tvBookingMoveOutBtn.apply {
                    isClickable = false
                    background = ResourcesCompat.getDrawable(context.resources,R.drawable.otp_box_background,null)
                    setTextColor(context.getColor(R.color.gray2))
                    text = String.format("%s %s",context.getString(R.string.move_out),
                        setDateFormat(model.moveoutDate.toString())
                    )
                }
            }

            tvBookingMoveOutBtn.setOnClickListener {
                if (model.moveOutReqFlag=="1"){
                    mToast(context,"Your request is under processing.")
                }else{
                    listener.onMoveOutBooking(model)
                }
            }
        }
    }

}