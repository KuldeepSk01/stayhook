package com.stayhook.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.stayhook.R
import com.stayhook.adapter.interfaces.OnMessageClickListener
import com.stayhook.databinding.ItemMessageLayoutBinding
import com.stayhook.model.Message

class MessageAdapter(
    val list: MutableList<Message>,
    val context: Context,
    val listener: OnMessageClickListener
) : RecyclerView.Adapter<MessageAdapter.MessageVM>() {
    inner class MessageVM(val b: ItemMessageLayoutBinding) : ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageVM {
        return MessageVM(
            DataBindingUtil.inflate<ItemMessageLayoutBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_message_layout,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MessageVM, position: Int) {
        val model = list[position]
        holder.b.apply {
            Glide.with(context).load(model.imageUrl).into(ivItemMessage)
            tvItemMessageName.text = model.name
            tvItemMessages.text = model.message
            if (model.isOnline!!) {
                ivOnlineIcon.setBackgroundResource(R.drawable.online_status_drawable)
            } else {
                ivOnlineIcon.setBackgroundResource(R.drawable.offline_status_drawable)
            }
            rlMessages.setOnClickListener {
                listener.onClickMessage(model)
            }
        }
    }

}