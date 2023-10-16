package com.stayhook.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.stayhook.R
import com.stayhook.databinding.ItemSelectRoomLayoutBinding
import com.stayhook.interfaces.OnRoomClickListener
import com.stayhook.model.Room

class SelectRoomAdapter(private val list: MutableList<Room>, private val context: Context,private val listener:OnRoomClickListener) :
    RecyclerView.Adapter<SelectRoomAdapter.SRVM>() {
    private var itemPosition = 0
    inner class SRVM(val b: ItemSelectRoomLayoutBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SRVM {
        val view = DataBindingUtil.inflate<ItemSelectRoomLayoutBinding>(
            LayoutInflater.from(parent.context), R.layout.item_select_room_layout, parent, false
        )
        return SRVM(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SRVM, position: Int) {
        val model = list[position]
        holder.b.apply {
            if (itemPosition==position){
                rlSelectRoom.background = context.resources.getDrawable(R.drawable.otp_box_outline_drawable,null)
            }else{
                rlSelectRoom.background = context.resources.getDrawable(R.drawable.otp_box_background,null)
            }
            tvRoomsTypeSR.text = "${model.roomType.toString()}"
            tvSqftSR.text = "${model.roomArea.toString()} sqft"
            tv1XAttachedSR.text = model.roomDescription.toString()
            tvRItemCostSR.text = "$${model.roomCharges.toString()}"
            rlSelectRoom.setOnClickListener {
                itemPosition = position
                notifyDataSetChanged()
                listener.onRoomClick(model)
            }
        }
    }
}