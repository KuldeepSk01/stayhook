package com.stayhook.screen.register

import androidx.lifecycle.MutableLiveData
import com.stayhook.R
import com.stayhook.base.BaseViewModel
import com.stayhook.model.request.UserRequest
import com.stayhook.model.response.OTPResponse
import com.stayhook.network.ApiResponse
import com.stayhook.validation.ValidationResult
import com.stayhook.validation.ValidationState

class RegisterViewModel(private val registerRepo: RegisterRepo) : BaseViewModel() {
    private val registerUserResponse = MutableLiveData<ApiResponse<OTPResponse>>()
    var validationRegisterData = MutableLiveData<ValidationState>()
    fun hitRegisterApi(request: UserRequest) {
        registerRepo.executeRegisterApi(request, registerUserResponse)
    }

    fun getRegisterResponse(): MutableLiveData<ApiResponse<OTPResponse>> {
        return registerUserResponse
    }


    fun isValidData(fullName: String?, mobile: String?) {
        if (fullName?.trim()
                ?.let { validator.validFullName(fullName) } != ValidationResult.SUCCESS
        ) {
            if (fullName?.trim()
                    ?.let { validator.validFullName(fullName) } == ValidationResult.EMPTY_FULL_NAME
            ) {
                validationRegisterData.postValue(
                    ValidationState(
                        ValidationResult.EMPTY_FULL_NAME,
                        R.string.error_full_name_empty
                    )
                )
                return
            }
        }
        if (mobile?.trim()?.let { validator.validMobileNo(mobile) } != ValidationResult.SUCCESS) {
            if (mobile?.trim()
                    ?.let { validator.validMobileNo(mobile) } == ValidationResult.EMPTY_MOBILE_NUMBER
            ) {
                validationRegisterData.postValue(
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
                validationRegisterData.postValue(
                    ValidationState(
                        ValidationResult.VALID_MOBILE_NUMBER,
                        R.string.error_mobile_length
                    )
                )
                return
            }
        }
        validationRegisterData.postValue(
            ValidationState(
                ValidationResult.SUCCESS,
                R.string.verify_success
            )
        )
    }
}