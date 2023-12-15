package com.stayhook.screen.dashboard.home.recommondationdetail.bookapartment

import androidx.lifecycle.MutableLiveData
import com.stayhook.base.BaseViewModel
import com.stayhook.base.CollectionBaseResponse
import com.stayhook.model.response.getpopertydetail.PropertyRoom
import com.stayhook.network.ApiResponse

class RoomViewModel(private val roomRepo: RoomRepo) : BaseViewModel() {
    private val propertyRoomResponse =
        MutableLiveData<ApiResponse<CollectionBaseResponse<PropertyRoom>>>()

    fun hitPropertyDetail(propertyId: String) {
        roomRepo.executePropertyRoom(propertyId, propertyRoomResponse)

    }

    fun getPropertyDetailResponse(): MutableLiveData<ApiResponse<CollectionBaseResponse<PropertyRoom>>> {
        return propertyRoomResponse
    }

}