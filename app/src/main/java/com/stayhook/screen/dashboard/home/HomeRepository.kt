package com.stayhook.screen.dashboard.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.stayhook.base.BaseRepository
import com.stayhook.model.response.home.HomeResponse
import com.stayhook.network.ApiResponse
import com.stayhook.util.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeRepository : BaseRepository() {

    fun executeHomePageAPi(responseLiveData: MutableLiveData<ApiResponse<HomeResponse>>) {
        val call = apiService.homePage()
        responseLiveData.postValue(ApiResponse.loading())

        call.enqueue(object : Callback<HomeResponse> {
            override fun onResponse(call: Call<HomeResponse>, response: Response<HomeResponse>) {
                try {
                    if (response.body()?.success!!) {
                        responseLiveData.postValue(ApiResponse.success(response.body()!!))
                    } else {
                        responseLiveData.postValue(ApiResponse.error(Throwable(response.body()?.message!!)))
                    }
                } catch (e: Exception) {
                    Log.d("HomeRepo", "onResponse: Error $e")
                    responseLiveData.postValue(ApiResponse.error(Throwable(e.message)))
                }

            }

            override fun onFailure(call: Call<HomeResponse>, t: Throwable) {
                if (t.message.equals("Software caused connection abort")) {
                    responseLiveData.postValue(ApiResponse.error(Throwable(Constants.NetworkConstant.CONNECTION_LOST)))
                } else {
                    Log.d("homeRepo", "onFailure: ${t.message}")
                    responseLiveData.postValue(ApiResponse.error(t))
                }
            }

        })

    }
}