package com.stayhook.model.request

import com.google.gson.annotations.SerializedName
import java.io.File
import java.io.Serializable

data class UserRequest(
    @SerializedName("id") var id: Long? = null,
    @SerializedName("first_name") var firstName: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("mobile") var mobile: String? = null,
    @SerializedName("userotp") var userotp: String? = null,
    @SerializedName("profile") var profile: File? = null,
    @SerializedName("gender") var gender: String? = null,
    @SerializedName("status") var status: Int? = null,
    @SerializedName("full_name") var fullName: String? = null,
    @SerializedName("token") var token: String? = null
) : Serializable
