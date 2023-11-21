package com.stayhook.screen.dashboard.account.mybooking

import android.media.session.MediaSession.Token
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.stayhook.base.BaseRepository
import com.stayhook.base.BaseResponse
import com.stayhook.base.CollectionBaseResponse
import com.stayhook.model.response.TokenCollectedResponse
import com.stayhook.model.response.getpopertydetail.GetPropertyDetail
import com.stayhook.network.ApiResponse
import com.stayhook.util.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyBookingRepo : BaseRepository() {
    fun executeTokenCollected(
        responseLiveData: MutableLiveData<ApiResponse<CollectionBaseResponse<TokenCollectedResponse>>>
    ) {
        val call = apiService.getTokenCollected()
        responseLiveData.postValue(ApiResponse.loading())
        call.enqueue(object : Callback<CollectionBaseResponse<TokenCollectedResponse>> {
            override fun onResponse(
                call: Call<CollectionBaseResponse<TokenCollectedResponse>>,
                response: Response<CollectionBaseResponse<TokenCollectedResponse>>
            ) {
                try {
                    if (response.body()?.success!!) {
                        responseLiveData.postValue(ApiResponse.success(response.body()!!))
                    } else {
                        responseLiveData.postValue(ApiResponse.error(Throwable(response.body()?.message)))
                    }
                } catch (e: Exception) {
                    Log.d("RecommendationRepo", "onResponse: error")
                }
            }

            override fun onFailure(call: Call<CollectionBaseResponse<TokenCollectedResponse>>, t: Throwable) {
                if (t.message.equals("Software caused connection abort")){
                    responseLiveData.postValue(ApiResponse.error(Throwable(Constants.NetworkConstant.CONNECTION_LOST)))
                }else{
                    Log.d("RegisterRepo", "onFailure: ${t.message}")
                    responseLiveData.postValue(ApiResponse.error(t))
                }
            }

        })
    }

}