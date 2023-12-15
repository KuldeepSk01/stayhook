package com.stayhook.screen.dashboard.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.stayhook.base.BaseRepository
import com.stayhook.base.BaseResponse
import com.stayhook.model.request.GetPropertyRequest
import com.stayhook.model.response.getpopertydetail.GetPropertyDetail
import com.stayhook.model.response.getproperty.GetPropertyBaseResponse
import com.stayhook.network.ApiResponse
import com.stayhook.util.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SeeAllItemRepo :BaseRepository(){
    fun executeGetProperty(
        req: GetPropertyRequest,
        responseLiveDataData: MutableLiveData<ApiResponse<GetPropertyBaseResponse>>
    ) {
        val call = apiService.getProperty(req)
        responseLiveDataData.postValue(ApiResponse.loading())
        call.enqueue(object : Callback<GetPropertyBaseResponse> {
            override fun onResponse(
                call: Call<GetPropertyBaseResponse>,
                response: Response<GetPropertyBaseResponse>
            ) {
                try {
                    if (response.body()?.success!!) {
                        responseLiveDataData.postValue(ApiResponse.success(response.body()!!))
                    } else {
                        responseLiveDataData.postValue(ApiResponse.error(Throwable(response.body()?.message)))
                    }
                } catch (e: Exception) {
                    Log.d("SeeAllItemRepo", "onResponse: error")
                }
            }

            override fun onFailure(call: Call<GetPropertyBaseResponse>, t: Throwable) {
                if (t.message.equals("Software caused connection abort")){
                    responseLiveDataData.postValue(ApiResponse.error(Throwable(Constants.NetworkConstant.CONNECTION_LOST)))
                }else{
                    Log.d("SeeAllItemRepo", "onFailure: ${t.message}")
                    responseLiveDataData.postValue(ApiResponse.error(t))
                }
            }

        })
    }
}