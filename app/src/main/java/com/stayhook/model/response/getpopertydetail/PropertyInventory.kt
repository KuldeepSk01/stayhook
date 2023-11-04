package com.stayhook.model.response.getpopertydetail

import com.google.gson.annotations.SerializedName

data class PropertyInventory(
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("inventory_category")
    val inventory_category: String,
    @SerializedName("inventory_sub_category")
    val inventory_sub_category: String,
    @SerializedName("inventory_type")
    val inventory_type: String
)