package com.stayhook.screen.dashboard.account.mybooking

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.stayhook.base.BaseRepository
import com.stayhook.base.CollectionBaseResponse
import com.stayhook.model.request.MoveOutBookingRequest
import com.stayhook.model.response.MyBookingResponse
import com.stayhook.model.response.SuccessErrorResponse
import com.stayhook.model.response.TokenCollectedResponse
import com.stayhook.network.ApiResponse
import com.stayhook.util.Constants
import com.stayhook.util.mLog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyBookingRepo : BaseRepository() {
    fun executeGetMyBooking(
        responseLiveData: MutableLiveData<ApiResponse<CollectionBaseResponse<MyBookingResponse>>>
    ) {
        val call = apiService.getMyBooking()
        responseLiveData.postValue(ApiResponse.loading())
        call.enqueue(object : Callback<CollectionBaseResponse<MyBookingResponse>> {
            override fun onResponse(
                call: Call<CollectionBaseResponse<MyBookingResponse>>,
                response: Response<CollectionBaseResponse<MyBookingResponse>>
            ) {
                try {
                    if (response.body()?.success!!) {
                        responseLiveData.postValue(ApiResponse.success(response.body()!!))
                    } else {
                        responseLiveData.postValue(ApiResponse.error(Throwable(response.body()?.message)))
                    }
                } catch (e: Exception) {
                    Log.d("RecommendationRepo", "onResponse: error")
                }
            }

            override fun onFailure(
                call: Call<CollectionBaseResponse<MyBookingResponse>>,
                t: Throwable
            ) {
                if (t.message.equals("Software caused connection abort")) {
                    responseLiveData.postValue(ApiResponse.error(Throwable(Constants.NetworkConstant.CONNECTION_LOST)))
                } else {
                    mLog("onFailure: ${t.message}")
                    responseLiveData.postValue(ApiResponse.error(t))
                }
            }

        })
    }



    fun executeMoveOutBooking(
        req:MoveOutBookingRequest,
        responseLiveData: MutableLiveData<ApiResponse<CollectionBaseResponse<SuccessErrorResponse>>>
    ) {
        val call = apiService.requestMoveOutBooking(req)
        responseLiveData.postValue(ApiResponse.loading())
        call.enqueue(object : Callback<CollectionBaseResponse<SuccessErrorResponse>> {
            override fun onResponse(
                call: Call<CollectionBaseResponse<SuccessErrorResponse>>,
                response: Response<CollectionBaseResponse<SuccessErrorResponse>>
            ) {
                try {
                    if (response.body()?.success!!) {
                        responseLiveData.postValue(ApiResponse.success(response.body()!!))
                    } else {
                        responseLiveData.postValue(ApiResponse.error(Throwable(response.body()?.message)))
                    }
                } catch (e: Exception) {
                    Log.d("RecommendationRepo", "onResponse: error")
                }
            }

            override fun onFailure(
                call: Call<CollectionBaseResponse<SuccessErrorResponse>>,
                t: Throwable
            ) {
                if (t.message.equals("Software caused connection abort")) {
                    responseLiveData.postValue(ApiResponse.error(Throwable(Constants.NetworkConstant.CONNECTION_LOST)))
                } else {
                    mLog("onFailure: ${t.message}")
                    responseLiveData.postValue(ApiResponse.error(t))
                }
            }

        })
    }

}