package com.stayhook.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stayhook.R
import com.stayhook.databinding.ItemTicketImagesLayoutBinding

class TicketImagesAdapter(private val list: MutableList<String>, val context: Context) :
    RecyclerView.Adapter<TicketImagesAdapter.TicketVM>() {
    inner class TicketVM(val b: ItemTicketImagesLayoutBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketVM {
        val view = DataBindingUtil.inflate<ItemTicketImagesLayoutBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_ticket_images_layout, parent, false
        )

        return TicketVM(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: TicketVM, position: Int) {
        val model = list[position]
        Glide.with(context).load(model).into(holder.b.ivOnboardingItem)
    }
}