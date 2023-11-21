package com.stayhook.screen.dashboard.account.mybooking

import androidx.lifecycle.MutableLiveData
import com.stayhook.base.BaseViewModel
import com.stayhook.base.CollectionBaseResponse
import com.stayhook.model.response.TokenCollectedResponse
import com.stayhook.network.ApiResponse

class MyBookingViewModel(private val myBookingRepo: MyBookingRepo) : BaseViewModel() {
    private val getTokenResponse =
        MutableLiveData<ApiResponse<CollectionBaseResponse<TokenCollectedResponse>>>()

    fun hitGetTokenCollected() {
        myBookingRepo.executeTokenCollected(getTokenResponse)
    }

    fun getTokenCollectedResponse(): MutableLiveData<ApiResponse<CollectionBaseResponse<TokenCollectedResponse>>> {
        return getTokenResponse
    }
}