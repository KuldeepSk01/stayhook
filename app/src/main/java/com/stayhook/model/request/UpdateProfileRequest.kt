package com.stayhook.model.request

import com.google.gson.annotations.SerializedName
import java.io.File
import java.io.Serializable

data class UpdateProfileRequest(
    @SerializedName("email")
    var email: String?=null,
    @SerializedName("full_name")
    var full_name: String?=null,
    @SerializedName("gender")
    var gender: String?=null,
    @SerializedName("mobile_no")
    var mobile_no: String?=null,
    @SerializedName("profile")
    var profile: Any?=null
):Serializable