package com.stayhook.model.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MyBookingResponse(
    @SerializedName("area")
    val area: String,
    @SerializedName("booking_id")
    val bookingId: Int,
    @SerializedName("city")
    val city: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("movein_date")
    val moveinDate: String,
    @SerializedName("movein_flag")
    val moveinFlag: String,
    @SerializedName("moveout_date")
    val moveoutDate: String,
    @SerializedName("moveout_flag")
    val moveoutFlag: String,
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
    val street: String,
    @SerializedName("moveout_req_flag")
    val moveOutReqFlag: String,
    @SerializedName("moveout_req_date")
    val moveOutReqDate: String
):Serializable