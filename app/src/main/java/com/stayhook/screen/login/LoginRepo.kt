package com.stayhook.screen.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.stayhook.base.BaseRepository
import com.stayhook.model.request.UserRequest
import com.stayhook.model.response.OTPResponse
import com.stayhook.network.ApiResponse
import com.stayhook.util.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepo : BaseRepository() {
    fun executeLoginApi(
        loginRequest: UserRequest, responseLiveData: MutableLiveData<ApiResponse<OTPResponse>>
    ) {
        val call = apiService.login(loginRequest)
        responseLiveData.postValue(ApiResponse.loading())

        call.enqueue(object : Callback<OTPResponse> {
            override fun onResponse(
                call: Call<OTPResponse>, response: Response<OTPResponse>
            ) {
                if (response.body()?.success!!) {
                    responseLiveData.postValue(ApiResponse.success(response.body()!!))
                } else {
                    responseLiveData.postValue(ApiResponse.error(Throwable(response.body()?.message!!)))
                }
            }

            override fun onFailure(call: Call<OTPResponse>, t: Throwable) {
                if (t.message.equals("Software caused connection abort")) {
                    responseLiveData.postValue(ApiResponse.error(Throwable(Constants.NetworkConstant.CONNECTION_LOST)))
                } else {
                    Log.d("LoginRepo", "onFailure: ${t.message}")
                    responseLiveData.postValue(ApiResponse.error(t))
                }
                
            }

        })
    }
}