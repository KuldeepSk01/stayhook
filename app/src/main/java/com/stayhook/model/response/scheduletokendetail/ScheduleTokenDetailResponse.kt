package com.stayhook.model.response.scheduletokendetail

import com.google.gson.annotations.SerializedName
import com.stayhook.model.response.TokenCollectedResponse

data class ScheduleTokenDetailResponse(
    @SerializedName("data")
    val data: TokenCollectedResponse,
    @SerializedName("history")
    val history: List<History>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int,
    @SerializedName("success")
    val success: Boolean
)