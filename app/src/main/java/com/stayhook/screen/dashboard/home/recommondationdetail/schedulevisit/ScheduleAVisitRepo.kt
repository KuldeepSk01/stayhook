package com.stayhook.screen.dashboard.home.recommondationdetail.schedulevisit

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.stayhook.base.BaseRepository
import com.stayhook.model.request.PropertyRoomRequest
import com.stayhook.model.request.ScheduleAVisitRequest
import com.stayhook.model.response.SuccessErrorResponse
import com.stayhook.network.ApiResponse
import com.stayhook.util.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ScheduleAVisitRepo : BaseRepository() {
    fun executeScheduleVisit(
        request: ScheduleAVisitRequest,
        responseLiveData: MutableLiveData<ApiResponse<SuccessErrorResponse>>
    ) {
        val call = apiService.scheduleAVisit(request)
        responseLiveData.postValue(ApiResponse.loading())
        call.enqueue(object : Callback<SuccessErrorResponse> {
            override fun onResponse(
                call: Call<SuccessErrorResponse>,
                response: Response<SuccessErrorResponse>
            ) {
                try {
                    if (response.body()?.success!!) {
                        responseLiveData.postValue(ApiResponse.success(response.body()!!))
                    } else {
                        responseLiveData.postValue(ApiResponse.error(Throwable(response.body()?.message)))
                    }
                } catch (e: Exception) {
                    responseLiveData.postValue(ApiResponse.error(Throwable(e)))
                    Log.d("RecommendationRepo", "onResponse: error")
                }
            }

            override fun onFailure(call: Call<SuccessErrorResponse>, t: Throwable) {
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