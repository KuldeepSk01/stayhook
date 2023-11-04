package com.stayhook.model.response.getpopertydetail

import com.google.gson.annotations.SerializedName

data class PropertyImage(
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String
)