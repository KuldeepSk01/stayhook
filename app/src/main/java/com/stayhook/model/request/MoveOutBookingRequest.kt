package com.stayhook.model.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MoveOutBookingRequest(
    @SerializedName("lead_id")
    var leadId:Int=0,
    @SerializedName("booking_id")
    var bookingId:Int=0,
    @SerializedName("moveoutDate")
    var moveoutDate:String?=null,
    @SerializedName("moveoutRemark")
    var moveoutRemark:String?=null
):Serializable
