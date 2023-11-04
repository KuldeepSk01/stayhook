package com.stayhook.screen.dashboard.account

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.stayhook.base.BaseRepository
import com.stayhook.base.BaseResponse
import com.stayhook.model.response.MyProfileResponse
import com.stayhook.network.ApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountRepo:BaseRepository() {
    fun executeMyProfileApi(
        responseLive: MutableLiveData<ApiResponse<BaseResponse<MyProfileResponse>>>
    ) {
        val call = apiService.getMyProfile()
        responseLive.postValue(ApiResponse.loading())

        call.enqueue(object : Callback<BaseResponse<MyProfileResponse>> {
            override fun onResponse(
                call: Call<BaseResponse<MyProfileResponse>>,
                response: Response<BaseResponse<MyProfileResponse>>
            ) {
                if (response.isSuccessful) {
                    if (response.body()?.success!!) {
                        responseLive.postValue(ApiResponse.success(response.body()!!))
                    } else {
                        responseLive.postValue(ApiResponse.error(Throwable(response.body()?.message!!)))
                    }
                }


            }

            override fun onFailure(call: Call<BaseResponse<MyProfileResponse>>, t: Throwable) {
                Log.d("LoginRepo", "onFailure: ${t.message}")
                Log.d("LoginRepo", "onFailure: ${t.stackTrace}")
                responseLive.postValue(ApiResponse.error(t))
            }

        })
    }
}