package com.stayhook.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.stayhook.R
import com.stayhook.databinding.ItemSelectRoomLayoutBinding
import com.stayhook.interfaces.OnRoomClickListener
import com.stayhook.model.response.getpopertydetail.PropertyRoom

class SelectRoomAdapter(
    private val list: MutableList<PropertyRoom>,
    private val context: Context,
    private val listener: OnRoomClickListener,
    private val roomOrBed: String
) : RecyclerView.Adapter<SelectRoomAdapter.SRVM>() {
    private var itemPosition = -1

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
            if (model.roomType == "Common Room") {
                tvAvailability.visibility=View.GONE
                rlPriceRoom.visibility=View.GONE
                tvRoomsTypeSR.text = model.roomType.toString()
                tvSqftSR.text = String.format("%s %s", model.roomArea, "sqft")
                tv1XAttachedSR.text = String.format("%s,%s", model.kitchen, model.bathroom)
                tvPrivacyRoomSR.text = model.roomPrivacy.toString()
            }else{
                when (model.availabele) {
                    context.getString(R.string.yes_text) -> {
                        tvAvailability.apply {
                            text = context.getString(R.string.available)
                            background = ResourcesCompat.getDrawable(
                                context.resources, R.drawable.available_drawable, null
                            )
                        }
                    }

                    context.getString(R.string.no_text) -> {
                        tvAvailability.apply {
                            text = context.getString(R.string.not_available)
                            background = ResourcesCompat.getDrawable(
                                context.resources, R.drawable.not_available_drawable, null
                            )
                        }
                    }

                }

                if (itemPosition == position) {
                    rlSelectRoom.background =ResourcesCompat.getDrawable(context.resources,R.drawable.selected_box_drawable,null)
                } else {
                    rlSelectRoom.background = ResourcesCompat.getDrawable(context.resources,R.drawable.otp_box_outline_drawable,null)
                }

                if (roomOrBed == context.getString(R.string.room_select)) {
                    tvRoomsTypeSR.text = model.roomType.toString()
                    //  tvAvailability.visibility=View.GONE
                } else {
                    tvPrivacyRoomSR.visibility = View.GONE
                    tvRoomsTypeSR.text = model.bedName
                }

                tvPrivacyRoomSR.visibility = View.VISIBLE
                tvSqftSR.text = String.format("%s %s", model.roomArea, "sqft")
                tv1XAttachedSR.text = String.format("%s,%s", model.kitchen, model.bathroom)
                tvPrivacyRoomSR.text = model.roomPrivacy.toString()
                tvRItemCostSR.text = model.price.toString()

                rlSelectRoom.setOnClickListener {
                    itemPosition = position
                    notifyDataSetChanged()
                    listener.onRoomClick(model)
                }
            }




        }
    }
}