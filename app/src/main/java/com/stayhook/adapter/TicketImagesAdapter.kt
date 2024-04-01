package com.stayhook.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stayhook.R
import com.stayhook.databinding.ItemTicketImagesLayoutBinding
import com.stayhook.model.TicketImagesModel

class TicketImagesAdapter(private val list: MutableList<TicketImagesModel>, val context: Context,private val uriORNot:Boolean,private val listener:OnImageClickListener) :
    RecyclerView.Adapter<TicketImagesAdapter.TicketVM>() {
    inner class TicketVM(val b: ItemTicketImagesLayoutBinding) : RecyclerView.ViewHolder(b.root)

    interface OnImageClickListener{
        fun onClickImage(model:TicketImagesModel)
    }
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
        holder.b.apply {
            if (uriORNot){
                ivOnboardingItem.setImageURI(model.uri)
            }else{
                Glide.with(context).load(model.url).placeholder(R.drawable.default_image).into(holder.b.ivOnboardingItem)
            }

            ivOnboardingItem.setOnClickListener {
                listener.onClickImage(model)
            }
        }

    }
}