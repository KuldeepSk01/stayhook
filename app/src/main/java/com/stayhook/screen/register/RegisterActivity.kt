package com.stayhook.screen.register

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.stayhook.R
import com.stayhook.base.BaseActivity
import com.stayhook.databinding.ActivityRegisterBinding
import com.stayhook.model.request.UserRequest
import com.stayhook.model.response.OTPResponse
import com.stayhook.network.ApiResponse
import com.stayhook.screen.login.LoginActivity
import com.stayhook.screen.verification.VerificationActivity
import com.stayhook.util.Constants.NetworkConstant.Companion.NO_INTERNET_AVAILABLE
import com.stayhook.util.CustomDialogs
import com.stayhook.util.CustomDialogs.showErrorMessage
import com.stayhook.util.Utility.isConnectionAvailable
import com.stayhook.validation.ValidationResult
import com.stayhook.validation.ValidationState
import org.koin.core.component.inject

class RegisterActivity : BaseActivity() {
    private lateinit var rBinding: ActivityRegisterBinding
    private val mViewModel: RegisterViewModel by inject()

    override val layoutId: Int
        get() = R.layout.activity_register

    override fun onViewInit(binding: ViewDataBinding?) {
        rBinding = binding as ActivityRegisterBinding
        rBinding.vm = mViewModel
        rBinding.apply {
            ivBackRegister.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
            validDataObserver()
            tvLoginTXTBtn.setOnClickListener {
                launchActivity(LoginActivity::class.java)
                finish()
            }
        }

    }

    private fun validDataObserver() {
        mViewModel.validationRegisterData.observe(this@RegisterActivity, registerValidDataObserver)
    }

    private val registerObserver: Observer<ApiResponse<OTPResponse>> by lazy {
        Observer<ApiResponse<OTPResponse>> {
            when (it.status) {
                ApiResponse.Status.LOADING -> {
                    showProgress()
                }

                ApiResponse.Status.SUCCESS -> {
                    hideProgress()
                    launchActivity(VerificationActivity::class.java, "MobileNo", it.data?.mobile!!)
                }

                ApiResponse.Status.ERROR -> {
                    hideProgress()
                    CustomDialogs.showErrorMessage(
                        this@RegisterActivity,
                        it.error?.message.toString()
                    )
                }

            }

        }
    }
    private val registerValidDataObserver:Observer<ValidationState> by lazy {
        Observer {
            when(it.msg){
                ValidationResult.EMPTY_FULL_NAME->{
                    rBinding.etUserNameOnRegister.error = getString(it.code)
                    rBinding.etUserNameOnRegister.requestFocus()
                }
                ValidationResult.EMPTY_MOBILE_NUMBER,ValidationResult.VALID_MOBILE_NUMBER->{
                    rBinding.etMobileOnRegister.error = getString(it.code)
                    rBinding.etMobileOnRegister.requestFocus()
                }
                ValidationResult.SUCCESS->{
                    if (!isConnectionAvailable()) {
                        showErrorMessage(this@RegisterActivity, NO_INTERNET_AVAILABLE)
                    }else{
                        val request = UserRequest().apply {
                            fullName = rBinding.etUserNameOnRegister.text.toString()
                            mobile = rBinding.etMobileOnRegister.text.toString()
                        }
                        mViewModel.hitRegisterApi(request)
                        mViewModel.getRegisterResponse().observe(this@RegisterActivity, registerObserver)
                    }
                }
                else->{}
            }

        }
    }

}