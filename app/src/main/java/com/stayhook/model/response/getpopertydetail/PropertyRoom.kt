package com.stayhook.model.response.getpopertydetail

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PropertyRoom(
    @SerializedName("icon") var icon: String?=null,
    @SerializedName("id") var id: Int=-1,
    @SerializedName("price") var price: Int=-1,
    @SerializedName("room_name") var roomType: String?=null,
    @SerializedName("room_type") var roomPrivacy: String?=null,
    @SerializedName("room_type_id") var roomTypeId: Int=-1,
    @SerializedName("room_area") var roomArea: String?=null,
    @SerializedName("bed_name") var bedName: String?=null,
    @SerializedName("property_id") var propertyId: String?=null,
    @SerializedName("bathroom") var bathroom: String?=null,
    @SerializedName("kitchen") var kitchen: String?=null,
    @SerializedName("available") var availabele: String?=null,

    @SerializedName("room_id") var roomId: String?=null,
    @SerializedName("room_bed") var roomBed: String?=null,
    @SerializedName("availability_date") var availabilityDate: String?=null,
    @SerializedName("token_amount") var tokenAmount: String?=null
):Serializable