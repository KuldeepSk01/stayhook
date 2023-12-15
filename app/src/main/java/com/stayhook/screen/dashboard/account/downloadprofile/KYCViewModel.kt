package com.stayhook.screen.dashboard.account.downloadprofile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.stayhook.base.BaseViewModel
import com.stayhook.base.CollectionBaseResponse
import com.stayhook.model.response.StateCityResponse
import com.stayhook.model.response.SuccessErrorResponse
import com.stayhook.network.ApiResponse
import okhttp3.MultipartBody

class KYCViewModel(private val mRepo: KYCRepo) : BaseViewModel() {

    private val updateKYCResponse = MutableLiveData<ApiResponse<SuccessErrorResponse>>()
     val stateResponse =
        MutableLiveData<ApiResponse<CollectionBaseResponse<StateCityResponse>>>()
     val cityResponse =
        MutableLiveData<ApiResponse<CollectionBaseResponse<StateCityResponse>>>()

    fun hitUpdateKYCApi(
        map: HashMap<String, Any>,
        frontAadhaarImg: MultipartBody.Part?,
        backAadhaarImg: MultipartBody.Part?,
        panImg: MultipartBody.Part?,
        policeVerifyImg: MultipartBody.Part?
    ) {
        mRepo.executeUpdateKYCApi(
            map,
            frontAadhaarImg,
            backAadhaarImg,
            panImg,
            policeVerifyImg,
            updateKYCResponse
        )

        Log.d("TAG", "KYVViewModel: aadhaar ${map["aadhaarNo"]} Pan ${map["panNo"]}")

    }

    fun getUpdateKYCResponse(): MutableLiveData<ApiResponse<SuccessErrorResponse>> {
        return updateKYCResponse
    }


    fun hitStateApi(countryId: String) {
        mRepo.executeGetStateApi(countryId,stateResponse)
    }

    fun hitCityApi(stateId: String) {
        mRepo.executeGetCityApi(stateId,cityResponse)
    }


}