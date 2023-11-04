package com.stayhook.koin

import android.content.Context
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.stayhook.application.StayHookApplication
import com.stayhook.network.ApiService
import com.stayhook.network.intercepter.NetworkInterceptor
import com.stayhook.preference.PreferenceHelper
import com.stayhook.preference.reflection.ReflectionUtil
import com.stayhook.util.Constants
import com.stayhook.util.Constants.PreferenceConstant.API_TIMEOUT
import com.stayhook.util.Constants.PreferenceConstant.PREFERENCE_NAME
import com.stayhook.validation.ValidationHelper
import com.stayhook.validation.Validator
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {
    single<Gson> {
        GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .setLenient().create()
    }

    single {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(NetworkInterceptor()).addInterceptor(loggingInterceptor)
            .connectTimeout(API_TIMEOUT, TimeUnit.MILLISECONDS)
            .writeTimeout(API_TIMEOUT, TimeUnit.MILLISECONDS)
            .readTimeout(API_TIMEOUT, TimeUnit.MILLISECONDS)

        val okHttpClient = httpClient.build()
        Retrofit.Builder().baseUrl(Constants.NetworkConstant.BASE_URL).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(get() as Gson)).build()/*
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
        */

    }

    single { (get<Retrofit>()).create(ApiService::class.java) }

    single {
        PreferenceHelper(
            (androidApplication() as StayHookApplication).getSharedPreferences(
                PREFERENCE_NAME, Context.MODE_PRIVATE
            )
        )
    }

    single {
        ReflectionUtil(get())
    }

    single {
        ValidationHelper()
    }
    single {
        Validator(get())
    }


}