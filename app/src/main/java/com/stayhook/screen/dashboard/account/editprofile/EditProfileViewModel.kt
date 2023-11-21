package com.stayhook.screen.dashboard.account.editprofile

import androidx.lifecycle.MutableLiveData
import com.stayhook.base.BaseViewModel
import com.stayhook.model.response.SuccessErrorResponse
import com.stayhook.network.ApiResponse
import okhttp3.MultipartBody

class EditProfileViewModel(private val editProfileRepo: EditProfileRepo) : BaseViewModel() {

    private val editProfileResponse = MutableLiveData<ApiResponse<SuccessErrorResponse>>()

    fun hitUpdateProfileApi(map: HashMap<String, Any>, image: MultipartBody.Part?) {
        editProfileRepo.executeUpdateProfileApi(map, image, editProfileResponse)
    }

    fun getUpdateProfileResponse(): MutableLiveData<ApiResponse<SuccessErrorResponse>> {
        return editProfileResponse
    }

}