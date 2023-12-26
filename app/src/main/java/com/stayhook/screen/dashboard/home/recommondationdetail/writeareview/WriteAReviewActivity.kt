package com.stayhook.screen.dashboard.home.recommondationdetail.writeareview

import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.stayhook.R
import com.stayhook.base.BaseActivity
import com.stayhook.databinding.ActivityWriteAReviewBinding
import com.stayhook.model.request.WriteReviewRequest
import com.stayhook.model.response.SuccessErrorResponse
import com.stayhook.network.ApiResponse
import com.stayhook.util.Constants
import com.stayhook.util.CustomDialogs
import com.stayhook.validation.ValidationResult
import com.stayhook.validation.ValidationState
import org.koin.core.component.inject


class WriteAReviewActivity : BaseActivity() {

    private lateinit var wBinding: ActivityWriteAReviewBinding
    private val mViewModel: WriteReviewViewModel by inject()
    override val layoutId: Int
        get() = R.layout.activity_write_a_review

    override fun onViewInit(binding: ViewDataBinding?) {
        wBinding = binding as ActivityWriteAReviewBinding
        wBinding.apply {
            toolbarWriteAReview.apply {
                ivToolBarBack.setOnClickListener {
                    onBackPressedDispatcher.onBackPressed()
                }
                tvToolBarTitle.text = getString(R.string.write_a_review)
            }
            submitBT.setOnClickListener {
                mViewModel.isValidData(
                    reviewTitleET.text.toString(),
                    nameET.text.toString(),
                    emailET.text.toString(),
                    reviewDescET.text.toString()
                )
                mViewModel.validationData.observe(this@WriteAReviewActivity, validateDataObserver)
            }
        }
    }

    private val validateDataObserver = Observer<ValidationState> {
        wBinding.apply {
            when (it.msg) {
                ValidationResult.EMPTY_TITLE -> {
                    reviewTitleET.requestFocus()
                    reviewTitleET.error = getString(it.code)
                }

                ValidationResult.EMPTY_FULL_NAME -> {
                    nameET.requestFocus()
                    nameET.error = getString(it.code)
                }

                ValidationResult.EMPTY_EMAIL, ValidationResult.INVALID_EMAIL -> {
                    emailET.requestFocus()
                    emailET.error = getString(it.code)
                }

                ValidationResult.SUCCESS -> {
                    val req = WriteReviewRequest().apply {
                        propertyId = mPref[Constants.DefaultConstants.SELECT_PROPERTY_ID, ""]
                        title = reviewTitleET.text.toString()
                        name = nameET.text.toString()
                        email = emailET.text.toString()
                        review = reviewDescET.text.toString()
                    }
                    mViewModel.hitAddReviewApi(req)
                    mViewModel.getAddReviewResponse()
                        .observe(this@WriteAReviewActivity, addReviewObserver)
                }

                else -> {}
            }

        }
    }

    private val addReviewObserver = Observer<ApiResponse<SuccessErrorResponse>> {
        when (it.status) {
            ApiResponse.Status.LOADING -> {
                showProgress()
            }

            ApiResponse.Status.SUCCESS -> {
                hideProgress()
                Toast.makeText(this@WriteAReviewActivity,it.data?.message.toString(),Toast.LENGTH_SHORT).show()

                finish()
            }

            ApiResponse.Status.ERROR -> {
                hideProgress()
                CustomDialogs.showErrorMessage(
                    this@WriteAReviewActivity,
                    it.error?.message.toString()
                )
            }
        }
    }
}