package com.stayhook.model.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class WriteReviewRequest(
    @SerializedName("email")
    var email: String?=null,
    @SerializedName("name")
    var name: String?=null,
    @SerializedName("property_id")
    var propertyId: String?=null,
    @SerializedName("rating")
    var rating: Int=0,
    @SerializedName("review")
    var review: String?=null,
    @SerializedName("title")
    var title: String?=null
):Serializable