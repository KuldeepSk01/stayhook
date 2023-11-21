package com.stayhook.base

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CollectionBaseResponse<T>(
    @SerializedName("status") val status: Int? = 0,
    @SerializedName("success") val success: Boolean = false,
    @SerializedName("message") val message: String?=null,
    @SerializedName("leadCount") val leadCount: Int?=-1,
    @SerializedName("data") val data: List<T>
) : Serializable
