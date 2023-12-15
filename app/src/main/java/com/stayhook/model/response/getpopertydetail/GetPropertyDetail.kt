package com.stayhook.model.response.getpopertydetail

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GetPropertyDetail(
    @SerializedName("about")
    val about: String,
    @SerializedName("area")
    val area: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("gender_type")
    val gender_type: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("is_wishlist")
    val is_wishlist: Int,
    @SerializedName("pincode")
    val pincode: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("property_images")
    val property_images: List<PropertyImage>,
    @SerializedName("property_inventory")
    val property_inventory: List<PropertyInventory>,
    @SerializedName("property_name")
    val property_name: String,
    @SerializedName("property_room")
    val property_room: List<PropertyRoom>,
    @SerializedName("property_type")
    val property_type: String,
    @SerializedName("property_type_id")
    val property_type_id: String,
    @SerializedName("rating")
    val rating: Double,
    @SerializedName("state")
    val state: String,
    @SerializedName("street")
    val street: String,
    @SerializedName("total_area")
    val total_area: String,
    @SerializedName("total_bath")
    val total_bath: Int,
    @SerializedName("total_bed")
    val total_bed: Int,
    @SerializedName("total_review")
    val total_review: Int,
    @SerializedName("property_map")
    val property_map: String

):Serializable