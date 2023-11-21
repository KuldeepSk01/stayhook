package com.stayhook.screen.dashboard.home.recommondationdetail.beds

import androidx.lifecycle.MutableLiveData
import com.stayhook.base.BaseViewModel
import com.stayhook.base.CollectionBaseResponse
import com.stayhook.model.response.getpopertydetail.PropertyRoom
import com.stayhook.network.ApiResponse

class BedsViewModel(private val mRepo: BedsRepo) : BaseViewModel() {
    private val propertyRoomBedsResponse =
        MutableLiveData<ApiResponse<CollectionBaseResponse<PropertyRoom>>>()

    fun hitPropertyRoomBeds(propertyId: String, roomId: String) {
        mRepo.executePropertyBed(propertyId, roomId, propertyRoomBedsResponse)
    }

    fun getPropertyRoomBedsResponse(): MutableLiveData<ApiResponse<CollectionBaseResponse<PropertyRoom>>> {
        return propertyRoomBedsResponse
    }

}