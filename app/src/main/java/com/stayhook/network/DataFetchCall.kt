package com.stayhook.network

import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response

abstract class DataFetchCall<ResultType>(private val mutableResponse : MutableLiveData<ApiResponse<ResultType>>) {

    abstract fun createCallAsync():Deferred<Response<ResultType>>
    fun execute(){
        GlobalScope.launch {
            try {
                mutableResponse.postValue(ApiResponse.loading())
                val request = createCallAsync()
                val response = request.await()

                if (response.body() != null){
                    if (response.code() == 200){
                        mutableResponse.postValue(ApiResponse.success(response.body()!!))
                        Log.d("ApiCall", "execute: response body ${response.body()}")
                    }
                }


            }catch (e:Exception){}
        }
    }
}