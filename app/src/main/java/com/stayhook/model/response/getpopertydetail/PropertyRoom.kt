package com.stayhook.model.response.getpopertydetail

import com.google.gson.annotations.SerializedName

data class PropertyRoom(
    @SerializedName("icon")
    val icon: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("price")
    val price: Int,
    @SerializedName("room_name")
    val room_name: String,
    @SerializedName("room_type")
    val room_type: String,
    @SerializedName("room_type_id")
    val room_type_id: String
)