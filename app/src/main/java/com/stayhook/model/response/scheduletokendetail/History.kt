package com.stayhook.model.response.scheduletokendetail

import com.google.gson.annotations.SerializedName

data class History(
    @SerializedName("created_at")
    val created_at: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("remark")
    val remark: String,
    @SerializedName("status")
    val status: String
)