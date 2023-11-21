package com.stayhook.model.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PropertyRoomRequest(
    @SerializedName("bed_id")
    var bedId :String?=null,
    @SerializedName("room_type")
    var roomType:String?=null,
    @SerializedName("token_amount")
    var tokenAmount:String?=null,
    @SerializedName("property_id")
    var propertyId:String?=null,
    @SerializedName("room_id")
    var roomId:String?=null,
    @SerializedName("room_rent")
    var roomRent:String?=null,
    @SerializedName("availability_date")
    var availabilityDate:String?=null,

):Serializable