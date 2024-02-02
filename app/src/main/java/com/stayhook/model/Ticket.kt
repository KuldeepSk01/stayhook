package com.stayhook.model

import java.io.Serializable

data class Ticket(
    var id:Long?=-1,
    var property:String?=null,
    var tenantName:String?=null,
    var issue:String?=null,
    var issueType:String?=null,
    var issueDetail:String?=null,
    var issueImage:String?=null,
    var status:String?=null,
    var isOpenTicket:Boolean=true
):Serializable
