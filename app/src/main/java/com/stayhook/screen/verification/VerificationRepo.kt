package com.stayhook.screen.verification

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.stayhook.base.BaseRepository
import com.stayhook.base.BaseResponse
import com.stayhook.model.request.UserRequest
import com.stayhook.model.response.OTPResponse
import com.stayhook.model.response.UserResponse
import com.stayhook.network.ApiResponse
import com.stayhook.util.Constants
import com.stayhook.util.mLog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VerificationRepo : BaseRepository() {

    fun executeVerifyApi(
        loginRequest: UserRequest,
        responseLive: MutableLiveData<ApiResponse<BaseResponse<UserResponse>>>
    ) {
        val call = apiService.verifyOTP(loginRequest)
        responseLive.postValue(ApiResponse.loading())
        Log.d("Verify OTP", "executeVerifyApi: $loginRequest")
        call.enqueue(object : Callback<BaseResponse<UserResponse>> {
            override fun onResponse(
                call: Call<BaseResponse<UserResponse>>,
                response: Response<BaseResponse<UserResponse>>
            ) {
                if (response.body()?.success!!) {
                    responseLive.postValue(ApiResponse.success(response.body()!!))
                } else {
                    responseLive.postValue(ApiResponse.error(Throwable(response.body()?.message!!)))
                }
            }

            override fun onFailure(call: Call<BaseResponse<UserResponse>>, t: Throwable) {
                Log.d("LoginRepo", "onFailure: ${t.message}")
                responseLive.postValue(ApiResponse.error(t))
            }

        })


    }

    fun executeResendApi(
        loginRequest: UserRequest,
        responseLive: MutableLiveData<ApiResponse<OTPResponse>>
    ) {
        val call = apiService.login(loginRequest)
        responseLive.postValue(ApiResponse.loading())

        call.enqueue(object : Callback<OTPResponse> {
            override fun onResponse(
                call: Call<OTPResponse>,
                response: Response<OTPResponse>
            ) {
                if (response.body()?.success!!) {
                    responseLive.postValue(ApiResponse.success(response.body()!!))
                } else {
                    responseLive.postValue(ApiResponse.error(Throwable(response.body()?.message!!)))
                }
            }

            override fun onFailure(call: Call<OTPResponse>, t: Throwable) {
                if (t.message.equals("Software caused connection abort")) {
                    responseLive.postValue(ApiResponse.error(Throwable(Constants.NetworkConstant.CONNECTION_LOST)))
                } else {
                    mLog("onFailure: ${t.message}")
                    responseLive.postValue(ApiResponse.error(t))
                }
            }

        })
    }
}