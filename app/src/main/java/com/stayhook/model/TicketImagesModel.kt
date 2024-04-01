package com.stayhook.model

import android.net.Uri
import java.io.Serializable

data class TicketImagesModel(
    val media_type: String,
    val url: String,
    //for testing
    var uri:Uri?=null
): Serializable