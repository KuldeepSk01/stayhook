package com.stayhook.model.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TokenCollectedResponse(
    @SerializedName("area")
    val area: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("pincode")
    val pincode: String,
    @SerializedName("property_image")
    val propertyImage: String,
    @SerializedName("property_name")
    val propertyName: String,
    @SerializedName("state")
    val state: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("street")
    val street: String,
    @SerializedName("type")
    val type: String
):Serializable