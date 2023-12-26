package com.stayhook.screen.dashboard.account.downloadprofile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.stayhook.base.BaseRepository
import com.stayhook.base.CollectionBaseResponse
import com.stayhook.model.response.StateCityResponse
import com.stayhook.model.response.SuccessErrorResponse
import com.stayhook.network.ApiResponse
import com.stayhook.util.Constants
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class KYCRepo : BaseRepository() {
    fun executeUpdateKYCApi(
        map: HashMap<String, Any>,
        frontAadhaarImg: MultipartBody.Part?,
        backAadhaarImg: MultipartBody.Part?,
        panImg: MultipartBody.Part?,
        policeVerifyImg: MultipartBody.Part?,
        responseLive: MutableLiveData<ApiResponse<SuccessErrorResponse>>
    ) {
        Log.d("TAG", "executeUpdateKYCApi: aadhaar ${map["aadhaarNo"]} Pan ${map["panNo"]}")

        val firstName =
            map["first_name"].toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val dob = map["dob"].toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val gender = map["gender"].toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val aadhaarNo = map["aadhaarNo"].toString()
             val aa =   aadhaarNo.toRequestBody("text/plain".toMediaTypeOrNull())
        val panNo = map["panNo"].toString()
        val pp= panNo.toRequestBody("text/plain".toMediaTypeOrNull())
        val address = map["address"].toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val state = map["state"].toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val city = map["city"].toString().toRequestBody("text/plain".toMediaTypeOrNull())

        Log.d("TAG", "executeUpdateKYCApi: aadhaar $aadhaarNo Pan $panNo")

        val call = apiService.updateKYC(firstName, dob, gender, aa,pp,address,state,city, frontAadhaarImg,backAadhaarImg,panImg,policeVerifyImg)
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
                    Log.d("KYCRepo", "onSuccess failure: $e")

                }
            }

            override fun onFailure(call: Call<SuccessErrorResponse>, t: Throwable) {
                Log.d("KYCRepo", "onFailure: ${t.message}")
                responseLive.postValue(ApiResponse.error(t))
            }

        })
    }

    fun executeGetStateApi(
        countryId:String,
        responseLive: MutableLiveData<ApiResponse<CollectionBaseResponse<StateCityResponse>>>
    ) {
        val call = apiService.getStates(countryId)
        responseLive.postValue(ApiResponse.loading())

        call.enqueue(object : Callback<CollectionBaseResponse<StateCityResponse>> {
            override fun onResponse(
                call: Call<CollectionBaseResponse<StateCityResponse>>, response: Response<CollectionBaseResponse<StateCityResponse>>
            ) {
                try {
                    if (response.body()?.success!!) {
                        responseLive.postValue(ApiResponse.success(response.body()!!))
                    } else {
                        responseLive.postValue(ApiResponse.error(Throwable(response.body()?.message!!)))
                    }
                } catch (e: Exception) {
                    responseLive.postValue(ApiResponse.error(e))
                    Log.d("KYCRepo", "onSuccess failure: $e")

                }
            }

            override fun onFailure(call: Call<CollectionBaseResponse<StateCityResponse>>, t: Throwable) {
                Log.d("KYCRepo", "onFailure: ${t.message}")
                responseLive.postValue(ApiResponse.error(t))
            }

        })
    }

    fun executeGetCityApi(
        stateId:String,
        responseLive: MutableLiveData<ApiResponse<CollectionBaseResponse<StateCityResponse>>>
    ) {
        val call = apiService.getCity(stateId)
        responseLive.postValue(ApiResponse.loading())

        call.enqueue(object : Callback<CollectionBaseResponse<StateCityResponse>> {
            override fun onResponse(
                call: Call<CollectionBaseResponse<StateCityResponse>>, response: Response<CollectionBaseResponse<StateCityResponse>>
            ) {
                try {
                    if (response.body()?.success!!) {
                        responseLive.postValue(ApiResponse.success(response.body()!!))
                    } else {
                        responseLive.postValue(ApiResponse.error(Throwable(response.body()?.message!!)))
                    }
                } catch (e: Exception) {
                    responseLive.postValue(ApiResponse.error(e))
                    Log.d("KYCRepo", "onSuccess failure: $e")

                }
            }

            override fun onFailure(call: Call<CollectionBaseResponse<StateCityResponse>>, t: Throwable) {
                if (t.message.equals("Software caused connection abort")) {
                    responseLive.postValue(ApiResponse.error(Throwable(Constants.NetworkConstant.CONNECTION_LOST)))
                } else {
                    Log.d("KYCRepo", "onFailure: ${t.message}")
                    responseLive.postValue(ApiResponse.error(t))
                }
            }

        })
    }


}