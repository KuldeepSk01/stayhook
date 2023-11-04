package com.stayhook.network.intercepter

import android.text.TextUtils
import android.util.Log
import com.stayhook.preference.PreferenceHelper
import com.stayhook.util.Constants.PreferenceConstant.AUTHORIZATION
import com.stayhook.util.Constants.PreferenceConstant.TOKEN
import okhttp3.Interceptor
import okhttp3.Response
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NetworkInterceptor() : Interceptor, KoinComponent {
    private val mPref: PreferenceHelper by inject()
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val token = mPref[TOKEN, ""]
        if (TextUtils.isEmpty(token)) {
            return chain.proceed(originalRequest)
        }
        val newRequest = originalRequest
            .newBuilder()
            .addHeader(
                AUTHORIZATION, "Bearer $token"
            )
            .build()
        Log.d("StayHook", "Authorization intercept: token--> $token")
        return chain.proceed(newRequest)
    }
}