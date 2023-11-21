package com.stayhook.adapter.interfaces

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.stayhook.R
import com.stayhook.databinding.ItemRoomTypeRdBinding
import com.stayhook.model.response.getpopertydetail.PropertyRoom

class AdapterRoomTypes(val list: MutableList<PropertyRoom>, val context: Context) :
    RecyclerView.Adapter<AdapterRoomTypes.RoomTypesVM>() {
    class RoomTypesVM(val b: ItemRoomTypeRdBinding) : ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomTypesVM {
        return RoomTypesVM(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_room_type_rd, parent, false
            )
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: RoomTypesVM, position: Int) {
        val model = list[position]
        holder.b.apply {
            Glide.with(context).load(model.icon).into(ivItemRoom)
            tvRoomNameRD.text = model.roomType
            tvRoomTypeRD.text = model.roomPrivacy
            tvCostItemRoom.text = "$${model.price.toString()}"
        }
    }
}