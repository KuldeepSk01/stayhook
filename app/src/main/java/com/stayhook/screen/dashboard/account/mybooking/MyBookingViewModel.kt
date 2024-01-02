package com.stayhook.screen.dashboard.account.mybooking

import androidx.lifecycle.MutableLiveData
import com.stayhook.base.BaseViewModel
import com.stayhook.base.CollectionBaseResponse
import com.stayhook.model.request.MoveOutBookingRequest
import com.stayhook.model.response.MyBookingResponse
import com.stayhook.model.response.SuccessErrorResponse
import com.stayhook.network.ApiResponse

class MyBookingViewModel(private val myBookingRepo: MyBookingRepo) : BaseViewModel() {
    private val getTokenResponse =
        MutableLiveData<ApiResponse<CollectionBaseResponse<MyBookingResponse>>>()
    private val moveOutBookingResponse =
        MutableLiveData<ApiResponse<CollectionBaseResponse<SuccessErrorResponse>>>()
    fun hitGetMyBookingApi() {
        myBookingRepo.executeGetMyBooking(getTokenResponse)
    }

    fun getMyBookingResponse(): MutableLiveData<ApiResponse<CollectionBaseResponse<MyBookingResponse>>> {
        return getTokenResponse
    }



    fun hitMoveOutBookingApi(req:MoveOutBookingRequest) {
        myBookingRepo.executeMoveOutBooking(req,moveOutBookingResponse)
    }

    fun getMoveOutBookingResponse(): MutableLiveData<ApiResponse<CollectionBaseResponse<SuccessErrorResponse>>> {
        return moveOutBookingResponse
    }
}