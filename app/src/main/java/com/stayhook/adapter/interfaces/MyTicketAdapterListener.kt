package com.stayhook.adapter.interfaces

import com.stayhook.model.Ticket

interface MyTicketAdapterListener {
    fun onTicketClick(model: Ticket)
}