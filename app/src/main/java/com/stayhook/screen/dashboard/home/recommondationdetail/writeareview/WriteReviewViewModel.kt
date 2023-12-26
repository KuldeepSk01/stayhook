package com.stayhook.screen.dashboard.home.recommondationdetail.writeareview

import androidx.lifecycle.MutableLiveData
import com.stayhook.R
import com.stayhook.base.BaseViewModel
import com.stayhook.model.request.WriteReviewRequest
import com.stayhook.model.response.SuccessErrorResponse
import com.stayhook.network.ApiResponse
import com.stayhook.validation.ValidationResult
import com.stayhook.validation.ValidationState

class WriteReviewViewModel(private val repo: WriteReviewRepo) : BaseViewModel() {
    var validationData = MutableLiveData<ValidationState>()

    private val addReviewResponse =
        MutableLiveData<ApiResponse<SuccessErrorResponse>>()

    fun hitAddReviewApi(request: WriteReviewRequest) {
        repo.executeAddReview(request, addReviewResponse)
    }

    fun getAddReviewResponse(): MutableLiveData<ApiResponse<SuccessErrorResponse>> {
        return addReviewResponse
    }




    fun isValidData(title:String?,fullName:String?,email:String?,review: String?) {
        if (title?.trim()?.let { validator.validTitleReview(title) }!= ValidationResult.SUCCESS){
            if (title?.trim()
                    ?.let { validator.validTitleReview(title) } == ValidationResult.EMPTY_TITLE
            ) {
                validationData.postValue(
                    ValidationState(
                        ValidationResult.EMPTY_TITLE,
                        R.string.error_title_empty
                    )
                )
                return
            }
        }

        if (fullName?.trim()?.let { validator.validFullName(fullName) }!= ValidationResult.SUCCESS){
            if (fullName?.trim()
                    ?.let { validator.validFullName(fullName) } == ValidationResult.EMPTY_FULL_NAME
            ) {
                validationData.postValue(
                    ValidationState(
                        ValidationResult.EMPTY_FULL_NAME,
                        R.string.error_full_name_empty
                    )
                )
                return
            }
        }

        if (email?.trim()?.let { validator.validEmail(email) }!= ValidationResult.SUCCESS){
            if (email?.trim()
                    ?.let { validator.validEmail(email) } == ValidationResult.EMPTY_EMAIL
            ) {
                validationData.postValue(
                    ValidationState(
                        ValidationResult.EMPTY_EMAIL,
                        R.string.error_email_empty
                    )
                )
                return
            }
//            if (email?.trim()
//                    ?.let { validator.validEmail(email) } == ValidationResult.INVALID_EMAIL
//            ) {
//                validationData.postValue(
//                    ValidationState(
//                        ValidationResult.INVALID_EMAIL,
//                        R.string.email_validation
//                    )
//                )
//                return
//            }
        }

        validationData.postValue(
            ValidationState(
                ValidationResult.SUCCESS,
                R.string.verify_success
            )
        )
    }


}