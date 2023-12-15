package com.stayhook.screen.dashboard.account.completeprofile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.stayhook.base.BaseRepository
import com.stayhook.model.response.SuccessErrorResponse
import com.stayhook.network.ApiResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CompleteProfileRepo : BaseRepository() {
    fun executeUpdateProfileApi(
        map: HashMap<String, Any>,
        image: MultipartBody.Part?,
        responseLive: MutableLiveData<ApiResponse<SuccessErrorResponse>>
    ) {
        val fullName =
            RequestBody.create("text/plain".toMediaTypeOrNull(), map["first_name"].toString())
        val email = RequestBody.create("text/plain".toMediaTypeOrNull(), map["email"].toString())
        val mobile = RequestBody.create("text/plain".toMediaTypeOrNull(), map["mobile"].toString())
        val gender = RequestBody.create("text/plain".toMediaTypeOrNull(), map["gender"].toString())

        val call = apiService.updateProfile(fullName, email, mobile, gender, image)
        responseLive.postValue(ApiResponse.loading())

        call.enqueue(object : Callback<SuccessErrorResponse> {
            override fun onResponse(
                call: Call<SuccessErrorResponse>, response: Response<SuccessErrorResponse>
            ) {
                try {
                    if (response.body()?.success!!) {
                        responseLive.postValue(ApiResponse.success(response.body()!!))
                    } else {
                        responseLive.postValue(ApiResponse.error(Throwable(response.body()?.message!!)))
                    }
                } catch (e: Exception) {
                    responseLive.postValue(ApiResponse.error(e))
                }
            }

            override fun onFailure(call: Call<SuccessErrorResponse>, t: Throwable) {
                Log.d("LoginRepo", "onFailure: ${t.message}")
                responseLive.postValue(ApiResponse.error(t))
            }

        })
    }

}