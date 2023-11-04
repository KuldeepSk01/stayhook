package com.stayhook.model.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class OTPResponse(
    @SerializedName("data") val `data`: List<Any>,
    @SerializedName("message") val message: String,
    @SerializedName("mobile") val mobile: String,
    @SerializedName("otp") val otp: String,
    @SerializedName("status") val status: Int,
    @SerializedName("success") val success: Boolean
) : Serializable


