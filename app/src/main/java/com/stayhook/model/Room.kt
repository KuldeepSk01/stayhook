package com.stayhook.model

data class Room(
    var roomId:Long?=-1,
    var roomArea: Int? = 0,
    var roomDescription: String? = null,
    var roomCharges: Int? = null,
    var roomType:String?=null,
    var roomPrivacy:String? =null
    )
