package com.stayhook.screen.dashboard.home.recommondationdetail.beds

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.stayhook.base.BaseRepository
import com.stayhook.base.CollectionBaseResponse
import com.stayhook.model.response.getpopertydetail.PropertyRoom
import com.stayhook.network.ApiResponse
import com.stayhook.util.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BedsRepo : BaseRepository() {
    fun executePropertyBed(
        propertyId: String,
        roomId: String,
        responseLiveData: MutableLiveData<ApiResponse<CollectionBaseResponse<PropertyRoom>>>
    ) {
        val call = apiService.getPropertyBed(propertyId,roomId)
        responseLiveData.postValue(ApiResponse.loading())
        call.enqueue(object : Callback<CollectionBaseResponse<PropertyRoom>> {
            override fun onResponse(
                call: Call<CollectionBaseResponse<PropertyRoom>>,
                response: Response<CollectionBaseResponse<PropertyRoom>>
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

            override fun onFailure(call: Call<CollectionBaseResponse<PropertyRoom>>, t: Throwable) {
                if (t.message.equals("Software caused connection abort")) {
                    responseLiveData.postValue(ApiResponse.error(Throwable(Constants.NetworkConstant.CONNECTION_LOST)))
                } else {
                    Log.d("RegisterRepo", "onFailure: ${t.message}")
                    responseLiveData.postValue(ApiResponse.error(t))
                }
            }

        })
    }
}