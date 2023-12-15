package com.stayhook.model.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class StateCityResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
) : Serializable