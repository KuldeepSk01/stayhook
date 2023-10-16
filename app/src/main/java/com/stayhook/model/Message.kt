package com.stayhook.model

import java.io.Serializable

data class Message(
    var id:Int?=-1,
    var imageUrl: String? = null,
    var name: String? = null,
    var message: String? = null,
    var time: String? = null,
    var isOnline: Boolean? = false,

):Serializable