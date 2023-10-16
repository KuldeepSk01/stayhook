package com.stayhook.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.stayhook.R
import com.stayhook.databinding.ItemSelectDurationBinding
import com.stayhook.model.Duration
import com.stayhook.model.Recommendation

class SelectDurationAdapter(val list: MutableList<Duration>, val context: Context) :
    RecyclerView.Adapter<SelectDurationAdapter.SDVM>() {
    private var itemPosition = -1
    inner class SDVM(val b: ItemSelectDurationBinding) : ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SDVM {
        val view = DataBindingUtil.inflate<ItemSelectDurationBinding>(
            LayoutInflater.from(parent.context), R.layout.item_select_duration, parent, false
        )
        return SDVM(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SDVM, position: Int) {
        val model = list[position]
        holder.b.apply {

            if (itemPosition==position){
                rlSelectDuration.background = context.resources.getDrawable(R.drawable.otp_box_outline_drawable,null)
            }else{
                rlSelectDuration.background = context.resources.getDrawable(R.drawable.otp_box_background,null)
            }
            tvMin3MonthDuration.text = "Min ${model.months.toString()} months"
            tvSavingPercent.text = "${model.chargePerMonth.toString()}%"
            tvEarlyCharge.text = "${model.chargeInfo.toString()}"
            tvFreeRelocation.text = "${model.reLocationInfo.toString()}"
            tvFreeUpgrade.text = "${model.upgradeInfo.toString()}"
            tv7DaysFreeTrailSD.text ="${ model.freeTrial.toString()} Days free trail"
            rlSelectDuration.setOnClickListener {
                itemPosition = position
                notifyDataSetChanged()
            }
        }
    }
}