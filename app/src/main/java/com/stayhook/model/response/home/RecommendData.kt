package com.stayhook.model.response.home

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RecommendData(
    @SerializedName("area") val area: String?=null,
    @SerializedName("city") val city: String?=null,
    @SerializedName("country") val country: String?=null,
    @SerializedName("gender_type") val gender_type: String?=null,
    @SerializedName("id") val id: Int,
    @SerializedName("is_wishlist") val is_wishlist: Int,
    @SerializedName("pincode") val pincode: String?=null,
    @SerializedName("price") val price: Int,
    @SerializedName("property_image") val property_image: String?=null,
    @SerializedName("property_name") val property_name: String?=null,
    @SerializedName("property_type") val property_type: String?=null,
    @SerializedName("rating") val rating: Double,
    @SerializedName("state") val state: String?=null,
    @SerializedName("street") val street: String?=null
):Serializable