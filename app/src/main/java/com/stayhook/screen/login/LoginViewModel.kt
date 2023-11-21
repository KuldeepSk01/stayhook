package com.stayhook.screen.login

import androidx.lifecycle.MutableLiveData
import com.stayhook.R
import com.stayhook.base.BaseViewModel
import com.stayhook.model.request.UserRequest
import com.stayhook.model.response.OTPResponse
import com.stayhook.network.ApiResponse
import com.stayhook.validation.ValidationResult
import com.stayhook.validation.ValidationState

class LoginViewModel(private val loginRepo: LoginRepo) : BaseViewModel() {
    private val userResponse = MutableLiveData<ApiResponse<OTPResponse>>()
    var validationLoginData = MutableLiveData<ValidationState>()

    fun hitLoginApi(loginRequest: UserRequest) {
        loginRepo.executeLoginApi(loginRequest, userResponse)
    }

    fun getLoginResponse(): MutableLiveData<ApiResponse<OTPResponse>> {
        return userResponse
    }



    fun isValidData(mobile: String?) {
        if (mobile?.trim()?.let { validator.validMobileNo(mobile) } != ValidationResult.SUCCESS) {
            if (mobile?.trim()
                    ?.let { validator.validMobileNo(mobile) } == ValidationResult.EMPTY_MOBILE_NUMBER
            ) {
                validationLoginData.postValue(
                    ValidationState(
                        ValidationResult.EMPTY_MOBILE_NUMBER,
                        R.string.error_mobile_empty
                    )
                )
                return
            }
            if (mobile?.trim()
                    ?.let { validator.validMobileNo(mobile) } == ValidationResult.VALID_MOBILE_NUMBER
            ) {
                validationLoginData.postValue(
                    ValidationState(
                        ValidationResult.VALID_MOBILE_NUMBER,
                        R.string.error_mobile_length
                    )
                )
                return
            }
        }
        validationLoginData.postValue(
            ValidationState(
                ValidationResult.SUCCESS,
                R.string.verify_success
            )
        )
    }


}