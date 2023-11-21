package com.stayhook.screen.dashboard.account.myschedule

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.stayhook.base.BaseRepository
import com.stayhook.base.CollectionBaseResponse
import com.stayhook.base.ErrorResponse
import com.stayhook.model.response.TokenCollectedResponse
import com.stayhook.network.ApiResponse
import com.stayhook.preference.reflection.ReflectionUtil
import com.stayhook.util.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyScheduledVisitRepo : BaseRepository() {
    fun executeScheduledToken(
        responseLiveData: MutableLiveData<ApiResponse<CollectionBaseResponse<TokenCollectedResponse>>>
    ) {
        val call = apiService.getScheduledToken()
        responseLiveData.postValue(ApiResponse.loading())
        call.enqueue(object : Callback<CollectionBaseResponse<TokenCollectedResponse>> {
            override fun onResponse(
                call: Call<CollectionBaseResponse<TokenCollectedResponse>>,
                response: Response<CollectionBaseResponse<TokenCollectedResponse>>
            ) {
                try {
                    if (response.body()?.success!!) {
                        responseLiveData.postValue(ApiResponse.success(response.body()!!))
                    } else {
                        responseLiveData.postValue(ApiResponse.error(Throwable(reflectionUtil.parseJson(response.body().toString(),ErrorResponse::class.java).message)))
                    }
                } catch (e: Exception) {
                    responseLiveData.postValue(ApiResponse.error(e))
                    Log.d("ScheduleVisitRepo", "onResponse: error")
                }
            }

            override fun onFailure(
                call: Call<CollectionBaseResponse<TokenCollectedResponse>>,
                t: Throwable
            ) {
                if (t.message.equals("Software caused connection abort")) {
                    responseLiveData.postValue(ApiResponse.error(Throwable(Constants.NetworkConstant.CONNECTION_LOST)))
                } else {
                    Log.d("ScheduleVisitRepo", "onFailure: ${t.message}")
                    responseLiveData.postValue(ApiResponse.error(t))
                }
            }

        })
    }

}