package com.stayhook.model.response.home

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class HomeResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("nearby_data")
    val nearby_data: List<RecommendData>,
    @SerializedName("recently_data")
    val recently_data: List<RecommendData>,
    @SerializedName("recommend_data")
    val recommend_data: List<RecommendData>,
    @SerializedName("status")
    val status: Int,
    @SerializedName("success")
    val success: Boolean
):Serializable