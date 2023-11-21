package com.stayhook.model.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ScheduleAVisitRequest(
    @SerializedName("room_type")
    var roomType:String?=null,
    @SerializedName("property_id")
    var propertyId:String?=null,
    @SerializedName("room_id")
    var roomId:String?=null,
    @SerializedName("room_rent")
    var roomRent:String?=null,
    @SerializedName("availability_date")
    var availabilityDate:String?=null,
    @SerializedName("availability_time")
    var availabilityTime:String?=null,
    @SerializedName("full_name")
    var fullName:String?=null,
    @SerializedName("mobile_no")
    var mobileNo:String?=null,
    @SerializedName("message")
    var message:String?=null,
    @SerializedName("email")
    var email:String?=null
):Serializable