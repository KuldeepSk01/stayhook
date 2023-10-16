package com.stayhook.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.stayhook.R
import com.stayhook.adapter.interfaces.NotificationClickListener
import com.stayhook.databinding.ItemNotificationLayoutBinding
import com.stayhook.model.Notification

class NotificationAdapter(val list: MutableList<Notification>,val context:Context,val listener:NotificationClickListener):RecyclerView.Adapter<NotificationAdapter.NotificationVM>() {
    inner class NotificationVM(val b:ItemNotificationLayoutBinding):ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationVM {
        val v = DataBindingUtil.inflate<ItemNotificationLayoutBinding>(LayoutInflater.from(parent.context),
            R.layout.item_notification_layout,parent,false
        )
        return NotificationVM(v)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: NotificationVM, position: Int) {
        val model = list[position]
        holder.b.apply {
            ivItemNotification.setBackgroundResource(model.icon)
            tvItemNotificationTitle.text = model.notifTitle
            tvItemNotificationSubTitle.text = model.notifSubTitle
            clItemNotification.setOnClickListener {
                listener.onNotificationClick(model)
            }
        }
    }


}