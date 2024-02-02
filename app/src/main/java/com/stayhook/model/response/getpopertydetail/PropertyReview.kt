package com.stayhook.model.response.getpopertydetail

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PropertyReview(
    @SerializedName("created_at")
    val created_at: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("rating")
    val rating: Int,
    @SerializedName("review")
    val review: String,
    @SerializedName("title")
    val title: String
):Serializable