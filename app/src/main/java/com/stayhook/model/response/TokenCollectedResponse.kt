package com.stayhook.model.response

import com.google.gson.annotations.SerializedName
import com.stayhook.model.response.scheduletokendetail.History
import java.io.Serializable

data class TokenCollectedResponse(
    @SerializedName("area")
    val area: String?=null,
    @SerializedName("city")
    val city: String?=null,
    @SerializedName("country")
    val country: String?=null,
    @SerializedName("id")
    val id: Int,
    @SerializedName("pincode")
    val pincode: String?=null,
    @SerializedName("property_image")
    val propertyImage: String?=null,
    @SerializedName("property_name")
    val propertyName: String?=null,
    @SerializedName("state")
    val state: String?=null,
    @SerializedName("status")
    val status: String?=null,
    @SerializedName("street")
    val street: String?=null,
    @SerializedName("type")
    val type: String?=null,
    @SerializedName("history")
    val history: List<History>?=null,
):Serializable