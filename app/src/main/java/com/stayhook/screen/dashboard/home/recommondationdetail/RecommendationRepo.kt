package com.stayhook.screen.dashboard.home.recommondationdetail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.stayhook.base.BaseRepository
import com.stayhook.base.BaseResponse
import com.stayhook.model.response.getpopertydetail.GetPropertyDetail
import com.stayhook.network.ApiResponse
import com.stayhook.util.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecommendationRepo : BaseRepository() {
    fun executePropertyDetail(
        propertyId: String,
        responseLiveData: MutableLiveData<ApiResponse<BaseResponse<GetPropertyDetail>>>
    ) {
        val call = apiService.getPropertyDetail(propertyId)
        responseLiveData.postValue(ApiResponse.loading())
        call.enqueue(object : Callback<BaseResponse<GetPropertyDetail>> {
            override fun onResponse(
                call: Call<BaseResponse<GetPropertyDetail>>,
                response: Response<BaseResponse<GetPropertyDetail>>
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

            override fun onFailure(call: Call<BaseResponse<GetPropertyDetail>>, t: Throwable) {
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