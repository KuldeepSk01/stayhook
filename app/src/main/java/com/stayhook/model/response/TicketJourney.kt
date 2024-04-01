package com.stayhook.model.response

import java.io.Serializable

data class TicketJourney(
    val created_at: String,
    val id: Int,
    val meeting_date: String,
    val meeting_time: String,

    val vendor: String,
    val inspection: String,
    val revenu: String,
    val inspection_date: String,
    val inspection_time: String,


    val remark: String,
    val status: String,
    val added_by_name:String,
    val added_by_type:String,
    val ticket_image: List<TicketImage>,

    ):Serializable