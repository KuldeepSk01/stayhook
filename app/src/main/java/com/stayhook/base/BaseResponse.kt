package com.stayhook.base

data class BaseResponse<T>(
    val statusCode:Int?=-1,
    val message:String?=null,
    val data:T
)
