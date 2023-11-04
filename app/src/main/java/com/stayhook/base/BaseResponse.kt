package com.stayhook.base

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class BaseResponse<T>(
    @SerializedName("status") val status: Int? = 0,
    @SerializedName("success") val success: Boolean? = false,
    @SerializedName("message") val message: String? = null,
    @SerializedName("data") val data: T
) : Serializable
