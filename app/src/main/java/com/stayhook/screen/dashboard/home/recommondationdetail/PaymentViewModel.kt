package com.stayhook.screen.dashboard.home.recommondationdetail

import androidx.lifecycle.MutableLiveData
import com.stayhook.base.BaseViewModel
import com.stayhook.model.request.PropertyRoomRequest
import com.stayhook.model.response.SuccessErrorResponse
import com.stayhook.network.ApiResponse

class PaymentViewModel(private val mRepo: PaymentRepo) : BaseViewModel() {
    private val tokenCollectedResponse =
        MutableLiveData<ApiResponse<SuccessErrorResponse>>()

    fun hitTokenCollected(request: PropertyRoomRequest) {
        mRepo.executeTokenCollected(request, tokenCollectedResponse)
    }

    fun getTokenCollectedResponse(): MutableLiveData<ApiResponse<SuccessErrorResponse>> {
        return tokenCollectedResponse
    }
}