package com.stayhook.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.stayhook.R
import com.stayhook.databinding.ItemMyTicketsLayoutBinding
import com.stayhook.model.Ticket

class MyTicketAdapter(val list: MutableList<Ticket>, val context: Context) :
    RecyclerView.Adapter<MyTicketAdapter.MyTicketVM>() {
    inner class MyTicketVM(val b: ItemMyTicketsLayoutBinding) : ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyTicketVM {
        return MyTicketVM(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_my_tickets_layout, parent, false
            )
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: MyTicketVM, position: Int) {
        val model = list[position]
        holder.b.apply {
            tvItemTicketStatus.text = "${model.id} - Open"
            tvItemTicketTitle.text = model.ticketTitle
            tvItemTicketDescription.text = model.ticketDescription
        }
    }

}