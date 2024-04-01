package com.stayhook.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.stayhook.R
import com.stayhook.adapter.interfaces.MyTicketAdapterListener
import com.stayhook.databinding.ItemMyTicketsLayoutBinding
import com.stayhook.model.Ticket
import com.stayhook.model.response.TicketResponse

class MyTicketAdapter(val list: MutableList<TicketResponse>, val context: Context,private val listener:MyTicketAdapterListener) :
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
            Glide.with(context).load(model.property_image).placeholder(R.drawable.default_image).into(ivItemTicketImg)
            tvItemTicketProperty.text = model.property_name
            tvItemTicketTenantName.text = model.tenant_name
            tvItemTicketTenantType.text  = model.issue
            tvItemTicketTenantIssueType.text = model.issue_type
            tvItemTicketTenantIssueDetail.text = model.issue_details
            /*when(model.status){
                "Open"->{

                }
                "In Process"->{

                }
                "closed"->{

                }
            }*/

            clItemTicket.setOnClickListener {
                listener.onTicketClick(model)
            }
        }
    }

}