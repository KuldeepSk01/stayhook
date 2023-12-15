package com.stayhook.model.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GetPropertyRequest(
    @SerializedName("page")
    var page:Int?=null,
    @SerializedName("property_type")
    var propertyType:String?=null,
    @SerializedName("location")
    var location:String?=null,
    @SerializedName("price_min")
    var priceMin:Int?=null,
    @SerializedName("price_max")
    var priceMax:Int?=null
):Serializable
/*
9811628206*/
