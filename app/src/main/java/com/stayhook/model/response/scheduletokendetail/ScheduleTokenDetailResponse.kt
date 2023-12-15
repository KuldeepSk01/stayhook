package com.stayhook.model.response.scheduletokendetail

import com.google.gson.annotations.SerializedName
import com.stayhook.model.response.TokenCollectedResponse
import java.io.Serializable

data class ScheduleTokenDetailResponse(
    @SerializedName("data")
    val data: TokenCollectedResponse,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int,
    @SerializedName("success")
    val success: Boolean
):Serializable