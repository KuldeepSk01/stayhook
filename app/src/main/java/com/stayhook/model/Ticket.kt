package com.stayhook.model

import java.io.Serializable

data class Ticket(
    var id:Long?=-1,
    var ticketTitle:String?=null,
    var ticketDescription:String?=null,
    var isOpenTicket:Boolean=true
):Serializable
