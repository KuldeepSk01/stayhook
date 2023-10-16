package com.stayhook.network.intercepter

import android.text.TextUtils
import android.util.Log
import com.stayhook.preference.AUTHORIZATION
import com.stayhook.preference.PreferenceHelper
import com.stayhook.preference.TOKEN
import okhttp3.Interceptor
import okhttp3.Response
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.w3c.dom.Text

class NetworkInterceptor():Interceptor,KoinComponent {
    val mPref : PreferenceHelper by inject()
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val token = mPref[TOKEN, ""]
        if (TextUtils.isEmpty(token)){
            return chain.proceed(originalRequest)
        }

        val newRequest = originalRequest
            .newBuilder()
            .addHeader(AUTHORIZATION,token)
            .build()
        Log.d("Authorization", "intercept: token--> $token")
        return chain.proceed(newRequest)
    }
}