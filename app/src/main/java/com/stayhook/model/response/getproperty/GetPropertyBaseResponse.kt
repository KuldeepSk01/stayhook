package com.stayhook.model.response.getproperty

import com.google.gson.annotations.SerializedName
import com.stayhook.model.response.home.RecommendData
import java.io.Serializable

data class GetPropertyBaseResponse(
    @SerializedName("count")
    val count: Int,
    @SerializedName("data")
    val `data`: List<RecommendData>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int,
    @SerializedName("success")
    val success: Boolean
):Serializable