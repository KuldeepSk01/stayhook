package com.stayhook.base

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ErrorResponse(
    @SerializedName("status") val status: Int? = -1,
    @SerializedName("success") val success: Boolean? = null,
    @SerializedName("message") val message: String? = null,
) : Serializable
