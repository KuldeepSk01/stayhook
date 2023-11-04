package com.stayhook.model.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserResponse(
    @SerializedName("id") var id: Long? = -1,
    @SerializedName("first_name") var firstName: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("mobile") var mobileNo: String? = null,
    @SerializedName("profile") var profile: String? = null,
    @SerializedName("gender") var gender: String? = null,
    @SerializedName("status") var status: Int? = null,
    @SerializedName("full_name") var fullName: String? = null,
    @SerializedName("token") var token: String? = null,
) : Serializable
