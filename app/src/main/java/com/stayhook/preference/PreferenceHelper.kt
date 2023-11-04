package com.stayhook.preference

import android.content.SharedPreferences
import com.stayhook.model.response.MyProfileResponse
import com.stayhook.model.response.UserResponse
import com.stayhook.preference.reflection.ReflectionUtil
import com.stayhook.util.Constants.PreferenceConstant.USER_DETAIL
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/*
this class helps for storing and retrieve the small amount of data in device storage
*/
class PreferenceHelper(private val sharedPref: SharedPreferences) : KoinComponent {
    private val reflectionUtil: ReflectionUtil by inject()
    fun put(key: String, value: String) {
        sharedPref.edit().putString(key, value).apply()
    }

    fun put(key: String, value: Boolean) {
        sharedPref.edit().putBoolean(key, value).apply()
    }

    fun put(key: String, value: Int) {
        sharedPref.edit().putInt(key, value).apply()
    }

    fun setUserDetail(userResponse: MyProfileResponse) {
        val userDetail = reflectionUtil.parseClassToJson(userResponse)
        sharedPref.edit().putString(USER_DETAIL, userDetail).apply()
    }

    fun getUserDetail(): MyProfileResponse {
        return reflectionUtil.parseJsonToClass(get(USER_DETAIL, "")!!, MyProfileResponse::class.java)
    }

    operator fun get(key: String, defaultValue: String): String? {
        return sharedPref.getString(key, defaultValue)
    }

    operator fun get(key: String, defaultValue: Int): Int {
        return sharedPref.getInt(key, defaultValue)
    }

    operator fun get(key: String, defaultValue: Boolean): Boolean {
        return sharedPref.getBoolean(key, defaultValue)
    }


    fun clearSharedPref() {
        sharedPref.edit().clear().apply()
    }

}