package com.stayhook.adapter.interfaces

import com.stayhook.model.response.TicketResponse

interface MyTicketAdapterListener {
    fun onTicketClick(model: TicketResponse)
}