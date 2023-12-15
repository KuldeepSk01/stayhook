package com.stayhook.model.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MyPaymentsResponse(
    @SerializedName("amount")
    val amount: String,
    @SerializedName("area")
    val area: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("day")
    val day: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("month")
    val month: String,
    @SerializedName("paydate")
    val payDate: String,
    @SerializedName("pincode")
    val pinCode: String,
    @SerializedName("property_image")
    val propertyImage: String,
    @SerializedName("property_name")
    val propertyName: String,
    @SerializedName("state")
    val state: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("street")
    val street: String
):Serializable