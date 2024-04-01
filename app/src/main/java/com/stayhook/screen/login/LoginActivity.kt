package com.stayhook.screen.login

import android.util.Log
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.stayhook.R
import com.stayhook.base.BaseActivity
import com.stayhook.databinding.ActivityLoginBinding
import com.stayhook.model.request.UserRequest
import com.stayhook.model.response.OTPResponse
import com.stayhook.network.ApiResponse
import com.stayhook.screen.register.RegisterActivity
import com.stayhook.screen.verification.VerificationActivity
import com.stayhook.util.Constants
import com.stayhook.util.CustomDialogs.showErrorMessage
import com.stayhook.util.Utility
import com.stayhook.util.Utility.isConnectionAvailable
import com.stayhook.util.mLog
import com.stayhook.util.mToast
import com.stayhook.validation.ValidationResult
import com.stayhook.validation.ValidationState
import org.koin.core.component.inject

class LoginActivity : BaseActivity() {
    private lateinit var loginBinding: ActivityLoginBinding
    private lateinit var mobileNo: String
    private val mViewModel: LoginViewModel by inject()

    override val layoutId: Int
        get() = R.layout.activity_login

    override fun onViewInit(binding: ViewDataBinding?) {
        loginBinding = binding as ActivityLoginBinding
        loginBinding.vm = mViewModel
        loginBinding.apply {
            tvSignUpAccountBtn.setOnClickListener {
                launchActivity(RegisterActivity::class.java)
                finish()
            }

            loginBtn.setOnClickListener {
                mViewModel.isValidData(etMobileOnLogin.text.toString())
                mViewModel.validationLoginData.observe(this@LoginActivity, loginValidDataObserver)
            }
        }


    }

    private val loginObserver: Observer<ApiResponse<OTPResponse>> by lazy {
        Observer<ApiResponse<OTPResponse>> {
            when (it.status) {
                ApiResponse.Status.LOADING -> {
                    Log.d("LoginActivity", "onLoading: login loading...")
                    showProgress()
                }

                ApiResponse.Status.SUCCESS -> {
                    hideProgress()

                    mLog("LoginActivity login data message ${it.data?.message}")
                    mToast(this@LoginActivity,it.data?.otp.toString())
                    launchActivity(VerificationActivity::class.java, "MobileNo", mobileNo)
                    finish()

                }

                ApiResponse.Status.ERROR -> {
                    hideProgress()
                    showErrorMessage(this@LoginActivity, it.error?.message.toString())
                    Log.d("LoginActivity", " login error ${it.error?.message}")
                }

            }

        }
    }



    private val loginValidDataObserver: Observer<ValidationState> by lazy {
        Observer {
            when (it.msg) {
                ValidationResult.EMPTY_MOBILE_NUMBER, ValidationResult.VALID_MOBILE_NUMBER -> {
                    loginBinding.etMobileOnLogin.error = getString(it.code)
                    loginBinding.etMobileOnLogin.requestFocus()
                }

                ValidationResult.SUCCESS -> {
                    if (!Utility.isNetworkAvailable(this@LoginActivity)) {
                        showErrorMessage(
                            this@LoginActivity,
                            Constants.NetworkConstant.NO_INTERNET_AVAILABLE
                        )
                    } else {

                        mobileNo = loginBinding.etMobileOnLogin.text.toString()
                        val loginRequest = UserRequest().apply {
                            mobile = mobileNo
                        }
                        mViewModel.hitLoginApi(loginRequest)
                        mViewModel.getLoginResponse().observe(this@LoginActivity, loginObserver)


                    }
                }

                else -> {}
            }

        }
    }

}