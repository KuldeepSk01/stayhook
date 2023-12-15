package com.stayhook.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.stayhook.R
import com.stayhook.databinding.ItemAmenitiesLayoutBinding
import com.stayhook.databinding.ItemTimeListDialogBinding
import com.stayhook.model.response.StateCityResponse

class ScheduledTimePickAdapter(val list: MutableList<StateCityResponse>, val context: Context,private val listener:OnScheduledTimePickerListener) :
    RecyclerView.Adapter<ScheduledTimePickAdapter.ScheduledTimePickVM>() {
     class ScheduledTimePickVM(val b: ItemTimeListDialogBinding) : ViewHolder(b.root)

    interface OnScheduledTimePickerListener{
        fun onTimePicker(model:StateCityResponse)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduledTimePickVM {
        return ScheduledTimePickVM(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.item_time_list_dialog, parent, false
            )
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ScheduledTimePickVM, position: Int) {
        val model = list[position]
        holder.b.apply {
            tvItemScheduleTime.text = model.name
            tvItemScheduleTime.setOnClickListener {
                listener.onTimePicker(model)
            }
        }
    }
}