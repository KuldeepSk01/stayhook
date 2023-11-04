package com.stayhook.screen.dashboard.home.recommondationdetail

import androidx.lifecycle.MutableLiveData
import com.stayhook.base.BaseResponse
import com.stayhook.base.BaseViewModel
import com.stayhook.model.response.getpopertydetail.GetPropertyDetail
import com.stayhook.network.ApiResponse

class RecommendationViewModel(private val repo: RecommendationRepo) : BaseViewModel() {
    private val propertyDetailResponse =
        MutableLiveData<ApiResponse<BaseResponse<GetPropertyDetail>>>()

    fun hitPropertyDetail(propertyId: String) {
        repo.executePropertyDetail(propertyId, propertyDetailResponse)
    }
    fun getPropertyDetailResponse(): MutableLiveData<ApiResponse<BaseResponse<GetPropertyDetail>>> {
        return propertyDetailResponse
    }
}