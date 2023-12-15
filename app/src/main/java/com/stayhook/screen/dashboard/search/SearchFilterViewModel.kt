package com.stayhook.screen.dashboard.search

import androidx.lifecycle.MutableLiveData
import com.stayhook.base.BaseViewModel
import com.stayhook.model.request.GetPropertyRequest
import com.stayhook.model.response.getproperty.GetPropertyBaseResponse
import com.stayhook.network.ApiResponse

class SearchFilterViewModel(private val repo: SearchRepo) : BaseViewModel() {
    private val propertyApiResponse = MutableLiveData<ApiResponse<GetPropertyBaseResponse>>()
    fun hitPropertyApi(req: GetPropertyRequest) {
        repo.executeGetProperty(req, propertyApiResponse)
    }

    fun getPropertyResponse(): MutableLiveData<ApiResponse<GetPropertyBaseResponse>> {
        return propertyApiResponse
    }

}