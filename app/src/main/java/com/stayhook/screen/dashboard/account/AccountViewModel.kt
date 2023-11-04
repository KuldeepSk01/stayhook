package com.stayhook.screen.dashboard.account

import androidx.lifecycle.MutableLiveData
import com.stayhook.base.BaseResponse
import com.stayhook.base.BaseViewModel
import com.stayhook.model.response.MyProfileResponse
import com.stayhook.network.ApiResponse

class AccountViewModel(private val accountRepo:AccountRepo):BaseViewModel() {
    private val myProfileResponse = MutableLiveData<ApiResponse<BaseResponse<MyProfileResponse>>>()
    fun hitMyProfileApi() {
        accountRepo.executeMyProfileApi(myProfileResponse)
    }

    fun getMyProfileResponse(): MutableLiveData<ApiResponse<BaseResponse<MyProfileResponse>>> {
        return myProfileResponse
    }

}