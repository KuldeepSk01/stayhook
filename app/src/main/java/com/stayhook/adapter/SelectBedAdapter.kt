package com.stayhook.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.stayhook.R
import com.stayhook.databinding.ItemSelectBedBinding
import com.stayhook.interfaces.OnRoomClickListener
import com.stayhook.model.Bed
import com.stayhook.model.response.getpopertydetail.PropertyRoom

class SelectBedAdapter(val list: MutableList<PropertyRoom>, val context: Context, private val listener: SelectBedListener
) :
    RecyclerView.Adapter<SelectBedAdapter.SBVM>() {
    private var myListener : SelectBedListener = listener

    inner class SBVM(val b: ItemSelectBedBinding) : RecyclerView.ViewHolder(b.root)

    interface SelectBedListener{
        fun onBedClick(model:PropertyRoom)
    }
    private var itemPosition = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SBVM {
        val view = DataBindingUtil.inflate<ItemSelectBedBinding>(
            LayoutInflater.from(parent.context), R.layout.item_select_bed, parent, false
        )
        return SBVM(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SBVM, position: Int) {
        val model = list[position]
        holder.b.apply {
            tvItemRoomNameSD.text = model.roomType
            tvItemBedSD.text = "(${model.roomBed})"

            if (itemPosition == position) {
                llItemSD.background =
                    context.resources.getDrawable(R.drawable.otp_box_outline_drawable, null)
            } else {
                llItemSD.background =
                    context.resources.getDrawable(R.drawable.otp_box_background, null)
            }
/*
            tvMonthsSb.text = "${model.roomBed.toString()} Beds"
            tvAttachedBathroomSB.text = "${model.bedDescription.toString()}"
            tvCostSB.text = "$${model.bedChargesPerMonth.toString()}"
           */
            llItemSD.setOnClickListener {
                itemPosition = position
                notifyDataSetChanged()
                listener.onBedClick(model)

            }
        }
    }
}