package com.stayhook.screen.dashboard.account.mytoken

import androidx.lifecycle.MutableLiveData
import com.stayhook.base.BaseViewModel
import com.stayhook.base.CollectionBaseResponse
import com.stayhook.model.response.TokenCollectedResponse
import com.stayhook.network.ApiResponse

class MyTokenViewModel(private val myBookingRepo: MyTokenRepo) : BaseViewModel() {
    private val getTokenResponse =
        MutableLiveData<ApiResponse<CollectionBaseResponse<TokenCollectedResponse>>>()

    fun hitGetTokenCollected() {
        myBookingRepo.executeTokenCollected(getTokenResponse)
    }

    fun getTokenCollectedResponse(): MutableLiveData<ApiResponse<CollectionBaseResponse<TokenCollectedResponse>>> {
        return getTokenResponse
    }
}