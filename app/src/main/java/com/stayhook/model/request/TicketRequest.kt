package com.stayhook.model.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TicketRequest(
    @SerializedName("property_id")
    var property_id: Int = -1,
    @SerializedName("issue_type")
    var issue_type: Int = -1,
    @SerializedName("issue")
    var issue: Int = -1,
    @SerializedName("issue_details")
    var issue_details: String? = null,
    @SerializedName("upload_document[]")
    var upload_document: java.util.ArrayList<String?>? = ArrayList<String?>()
) : Serializable
