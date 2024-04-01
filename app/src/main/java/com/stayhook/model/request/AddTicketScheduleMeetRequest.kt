package com.stayhook.model.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AddTicketScheduleMeetRequest(
    @SerializedName("comment")
    var comment: String?=null,
    @SerializedName("meeting_date")
    var meeting_date: String?=null,
    @SerializedName("meeting_time")
    var meeting_time: String?=null,
    @SerializedName("ticket_id")
    var ticket_id: Int=-1
):Serializable