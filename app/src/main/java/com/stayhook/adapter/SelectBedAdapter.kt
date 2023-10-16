package com.stayhook.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.stayhook.R
import com.stayhook.databinding.ItemSelectBedBinding
import com.stayhook.model.Bed

class SelectBedAdapter(val list: MutableList<Bed>, val context: Context) :
    RecyclerView.Adapter<SelectBedAdapter.SBVM>() {
    inner class SBVM(val b: ItemSelectBedBinding) : RecyclerView.ViewHolder(b.root)

    private var itemPosition = -1
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
            tvMonthsSb.text = "${model.beds.toString()} Beds"
            tvAttachedBathroomSB.text = "${model.bedDescription.toString()}"
            tvCostSB.text = "$${model.bedChargesPerMonth.toString()}"
            if (itemPosition == position) {
                rlBedLayout.background =
                    context.resources.getDrawable(R.drawable.otp_box_outline_drawable, null)
            } else {
                rlBedLayout.background =
                    context.resources.getDrawable(R.drawable.otp_box_background, null)
            }
            rlBedLayout.setOnClickListener {
                itemPosition = position
                notifyDataSetChanged()
            }
        }
    }
}