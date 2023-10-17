package com.stayhook.koin

import android.content.Context
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.stayhook.application.StayHookApplication
import com.stayhook.preference.PREFERENCE_NAME
import com.stayhook.preference.PreferenceHelper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single{
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(loggingInterceptor)
        /*
        * connectTimeout
        * writeTimeOut
        * readTimeout
        * addInterceptor(NetworkInterceptor())
        * */

        val okHttpClient = httpClient.build()
        Retrofit.Builder()
            .baseUrl("")
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(get() as Gson))
            .build()
    }


    single {
        PreferenceHelper(
            (androidApplication() as StayHookApplication).getSharedPreferences(
                PREFERENCE_NAME,
                Context.MODE_PRIVATE
            )
        )
    }
}