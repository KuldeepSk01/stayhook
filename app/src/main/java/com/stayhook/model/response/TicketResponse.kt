package com.stayhook.model.response

import java.io.Serializable

data class TicketResponse(
    val area: String,
    val city: String,
    val country: String,
    val created_at: String,
    val id: Int,
    val issue: String,
    val issue_details: String,
    val issue_type: String,
    val pincode: String,
    val property_image: String,
    val property_name: String,
    val state: String,
    val status: String,
    val street: String,
    val tenant_name: String
):Serializable