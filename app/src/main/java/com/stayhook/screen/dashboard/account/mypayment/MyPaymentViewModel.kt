package com.stayhook.screen.dashboard.account.mypayment

import androidx.lifecycle.MutableLiveData
import com.stayhook.base.BaseViewModel
import com.stayhook.base.CollectionBaseResponse
import com.stayhook.model.response.MyPaymentsResponse
import com.stayhook.network.ApiResponse

class MyPaymentViewModel(private val mRepo:MyPaymentRepo):BaseViewModel() {
    val paymentsResponse = MutableLiveData<ApiResponse<CollectionBaseResponse<MyPaymentsResponse>>>()
    fun hitMyPayment(type:String,page:String){
        mRepo.executeMyPayment(type,page,paymentsResponse)
    }



}