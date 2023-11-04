package com.stayhook.network

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.stayhook.base.ErrorResponse
import com.stayhook.preference.reflection.ReflectionUtil
import com.stayhook.util.Constants
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response

abstract class DataFetchCall<ResultType>(private val mutableResponse: MutableLiveData<ApiResponse<ResultType>>) {

    abstract fun createCallAsync(): Deferred<Response<ResultType>>
    fun execute() {
        GlobalScope.launch {
            try {
                mutableResponse.postValue(ApiResponse.loading())
                val request = createCallAsync()
                val response = request.await()

                if (response.body() != null) {
                    if (response.code() == Constants.NetworkConstant.API_SUCCESS) {
                        mutableResponse.postValue(ApiResponse.success(response.body()!!))
                        Log.d("ApiCall", "execute: response body ${response.body()}")
                    }
                } else if (response.code() == Constants.NetworkConstant.API_AUTH_ERROR) {
                    mutableResponse.postValue(
                        ApiResponse.error(
                            Throwable("session expired")
                        )
                    )
                } else {
                    mutableResponse.postValue(
                        ApiResponse.error(
                            Throwable(
                                ReflectionUtil(Gson()).parseJson(
                                    response.errorBody().toString(),
                                    ErrorResponse::class.java
                                ).message
                            )
                        )
                    )
                }
            } catch (e: Exception) {
                mutableResponse.postValue(ApiResponse.error(e))
            }
        }
    }
}