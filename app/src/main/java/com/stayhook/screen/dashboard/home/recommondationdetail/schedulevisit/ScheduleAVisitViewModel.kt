package com.stayhook.screen.dashboard.home.recommondationdetail.schedulevisit

import androidx.lifecycle.MutableLiveData
import com.stayhook.R
import com.stayhook.base.BaseViewModel
import com.stayhook.model.request.PropertyRoomRequest
import com.stayhook.model.request.ScheduleAVisitRequest
import com.stayhook.model.response.SuccessErrorResponse
import com.stayhook.network.ApiResponse
import com.stayhook.validation.ValidationResult
import com.stayhook.validation.ValidationState

class ScheduleAVisitViewModel(private val mRepo: ScheduleAVisitRepo) : BaseViewModel() {
    private val scheduleVisitResponse =
        MutableLiveData<ApiResponse<SuccessErrorResponse>>()
    var validationRegisterData = MutableLiveData<ValidationState>()


    fun hitScheduleVisit(request: ScheduleAVisitRequest) {
        mRepo.executeScheduleVisit(request, scheduleVisitResponse)
    }

    fun getScheduleVisitResponse(): MutableLiveData<ApiResponse<SuccessErrorResponse>> {
        return scheduleVisitResponse
    }

    fun isValidData(fullName:String?,email:String?,message:String?,mobile: String?) {
        if (fullName?.trim()?.let { validator.validFullName(fullName) }!=ValidationResult.SUCCESS){
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

        if (email?.trim()?.let { validator.validEmail(email) }!=ValidationResult.SUCCESS){
            if (email?.trim()
                    ?.let { validator.validEmail(email) } == ValidationResult.EMPTY_EMAIL
            ) {
                validationRegisterData.postValue(
                    ValidationState(
                        ValidationResult.EMPTY_EMAIL,
                        R.string.error_email_empty
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