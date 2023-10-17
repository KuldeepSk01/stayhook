package com.stayhook.base

data class CollectionBaseResponse<T>(
    val statusCode:Int?=-1,
    val message:String?=null,
    val data:List<T>
)
