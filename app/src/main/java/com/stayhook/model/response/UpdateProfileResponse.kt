package com.stayhook.model.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UpdateProfileResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String
) : Serializable