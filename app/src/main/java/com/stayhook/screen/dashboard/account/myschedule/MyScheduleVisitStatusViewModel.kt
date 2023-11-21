package com.stayhook.screen.dashboard.account.myschedule

import androidx.lifecycle.MutableLiveData
import com.stayhook.base.BaseViewModel
import com.stayhook.model.response.scheduletokendetail.ScheduleTokenDetailResponse
import com.stayhook.network.ApiResponse

class MyScheduleVisitStatusViewModel(private val mRepo: MyScheduledVisitStatusRepo) :
    BaseViewModel() {
    private val getTokenResponse =
        MutableLiveData<ApiResponse<ScheduleTokenDetailResponse>>()

    fun hitGetScheduledTokenDetail(tokenId: String) {
        mRepo.executeScheduledTokenDetail(tokenId, getTokenResponse)
    }

    fun getScheduledTokenDetailResponse(): MutableLiveData<ApiResponse<ScheduleTokenDetailResponse>> {
        return getTokenResponse
    }
}