package com.stayhook.preference.reflection

import com.google.gson.Gson

/*Reflection util class contains util method for api*/
data class ReflectionUtil(
    private val gson: Gson
) {

    fun <T> parseJson(json: String, classType: Class<T>): T {
        return Gson().fromJson(json, classType)
    }

    fun parseClassToJson(classType: Any): String {
        val gson = Gson()
        return gson.toJson(classType)
    }

    fun <T> parseJsonToClass(json: String, classType: Class<T>): T {
        return Gson().fromJson(json, classType)
    }

}

