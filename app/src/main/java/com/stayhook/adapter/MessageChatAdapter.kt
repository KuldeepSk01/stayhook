package com.stayhook.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.stayhook.R
import com.stayhook.databinding.ItemLeftChatBinding
import com.stayhook.databinding.ItemRightChatBinding
import com.stayhook.model.Message

class MessageChatAdapter(val list: MutableList<Message>, val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    class MChatLeftVM(val b: ItemLeftChatBinding) : ViewHolder(b.root)
    class MChatRightVM(val b: ItemRightChatBinding) : ViewHolder(b.root)
    companion object {
        const val ITEM_1 = 1
        const val ITEM_2 = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_1 -> {
                MChatLeftVM(
                    DataBindingUtil.inflate<ItemLeftChatBinding>(
                        LayoutInflater.from(parent.context),
                        R.layout.item_left_chat,
                        parent,
                        false
                    )
                )
            }

            else -> {
                MChatRightVM(
                    DataBindingUtil.inflate<ItemRightChatBinding>(
                        LayoutInflater.from(parent.context),
                        R.layout.item_right_chat,
                        parent,
                        false
                    )
                )
            }

        }
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MChatLeftVM) {
            val leftModel = list[position]
            holder.b.apply {
                tvItemLeftChat.text = leftModel.message
            }
        }else if (holder is MChatRightVM) {
            val rightModel = list[position]
            holder.b.apply {
                tvChatRight.text = rightModel.message
            }

        }else{

            }
        }
/*
        when (holder.itemViewType) {
            ITEM_1 -> {
                val leftChatBinding = holder is MChatLeftVM
                val leftModel = list[position]
                leftChatBinding.b.apply {
                    tvItemLeftChat.text = leftModel.message
                }
            }

            else -> {
                val rightChatBinding = holder as MChatLeftVM
                val rightModel = list[position]
                rightChatBinding.b.apply {
                    tvItemLeftChat.text = rightModel.message
                }
            }
        }
*/
  //  }

    override fun getItemViewType(position: Int): Int {
        val m = list[position]
        return if (m.userType == ITEM_1) {
            ITEM_1
        } else {
            ITEM_2
        }
    }


}