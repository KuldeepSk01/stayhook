package com.stayhook.model

data class Ticket(
    var id:Long?=-1,
    var ticketTitle:String?=null,
    var ticketDescription:String?=null,
    var isOpenTicket:Boolean=true
)
