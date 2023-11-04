package com.stayhook.screen.verification

import androidx.lifecycle.MutableLiveData
import com.stayhook.base.BaseResponse
import com.stayhook.base.BaseViewModel
import com.stayhook.model.request.UserRequest
import com.stayhook.model.response.OTPResponse
import com.stayhook.model.response.UserResponse
import com.stayhook.network.ApiResponse

class VerificationViewModel(private val verifyRepo: VerificationRepo) : BaseViewModel() {


    private val userResponse = MutableLiveData<ApiResponse<BaseResponse<UserResponse>>>()
    private val resendOTPResponse = MutableLiveData<ApiResponse<OTPResponse>>()
    fun hitVerifyOTPApi(loginRequest: UserRequest) {
        verifyRepo.executeVerifyApi(loginRequest, userResponse)
    }

    fun getVerifyOTPResponse(): MutableLiveData<ApiResponse<BaseResponse<UserResponse>>> {
        return userResponse
    }
    fun hitResendOTPApi(loginRequest: UserRequest) {
        verifyRepo.executeResendApi(loginRequest, resendOTPResponse)
    }

    fun getResendOTPResponse(): MutableLiveData<ApiResponse<OTPResponse>> {
        return resendOTPResponse
    }





}