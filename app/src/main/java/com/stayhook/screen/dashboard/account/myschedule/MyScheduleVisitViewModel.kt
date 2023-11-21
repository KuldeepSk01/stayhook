package com.stayhook.screen.dashboard.account.myschedule

import androidx.lifecycle.MutableLiveData
import com.stayhook.base.BaseViewModel
import com.stayhook.base.CollectionBaseResponse
import com.stayhook.model.response.TokenCollectedResponse
import com.stayhook.network.ApiResponse

class MyScheduleVisitViewModel(private val mRepo: MyScheduledVisitRepo) : BaseViewModel() {
    private val getTokenResponse =
        MutableLiveData<ApiResponse<CollectionBaseResponse<TokenCollectedResponse>>>()

    fun hitGetScheduleToken() {
        mRepo.executeScheduledToken(getTokenResponse)
    }

    fun getScheduledTokenResponse(): MutableLiveData<ApiResponse<CollectionBaseResponse<TokenCollectedResponse>>> {
        return getTokenResponse
    }
}