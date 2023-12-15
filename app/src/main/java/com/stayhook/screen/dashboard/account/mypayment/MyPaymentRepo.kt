package com.stayhook.screen.dashboard.account.mypayment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.stayhook.base.BaseRepository
import com.stayhook.base.CollectionBaseResponse
import com.stayhook.base.ErrorResponse
import com.stayhook.model.response.MyPaymentsResponse
import com.stayhook.model.response.TokenCollectedResponse
import com.stayhook.network.ApiResponse
import com.stayhook.util.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPaymentRepo : BaseRepository() {
    fun executeMyPayment(type:String,page:String,
        responseLiveData: MutableLiveData<ApiResponse<CollectionBaseResponse<MyPaymentsResponse>>>
    ) {
        val call = apiService.myPayments(type,page)
        responseLiveData.postValue(ApiResponse.loading())
        call.enqueue(object : Callback<CollectionBaseResponse<MyPaymentsResponse>> {
            override fun onResponse(
                call: Call<CollectionBaseResponse<MyPaymentsResponse>>,
                response: Response<CollectionBaseResponse<MyPaymentsResponse>>
            ) {
                try {
                    if (response.body()?.success!!) {
                        responseLiveData.postValue(ApiResponse.success(response.body()!!))
                    } else {
                        responseLiveData.postValue(
                            ApiResponse.error(Throwable(reflectionUtil.parseJson(response.body().toString(),
                                ErrorResponse::class.java).message)))
                    }
                } catch (e: Exception) {
                    responseLiveData.postValue(ApiResponse.error(e))
                    Log.d("MyPaymentRepo", "onResponse: error")
                }
            }

            override fun onFailure(
                call: Call<CollectionBaseResponse<MyPaymentsResponse>>,
                t: Throwable
            ) {
                if (t.message.equals("Software caused connection abort")) {
                    responseLiveData.postValue(ApiResponse.error(Throwable(Constants.NetworkConstant.CONNECTION_LOST)))
                } else {
                    Log.d("MyPaymentRepo", "onFailure: ${t.message}")
                    responseLiveData.postValue(ApiResponse.error(t))
                }
            }

        })
    }
}