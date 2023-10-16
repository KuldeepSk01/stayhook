package com.stayhook.model

import java.io.Serializable

data class
Recommendation(
    var imgUrl:String?=null,
    var name: String?=null,
    var apartmentType: String?=null,
    var location: String?=null,
    var price: String?=null,
    var rating:String?=null,
    var gender:String?="Men",
    var imagesUrls:MutableList<String>?= mutableListOf<String>()
):Serializable