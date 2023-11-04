package com.stayhook.screen.login

import android.util.Log
import android.widget.Toast
import androidx.databinding.ViewDataBinding
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
import com.stayhook.validation.ValidationResult
import com.stayhook.validation.ValidationState
import org.koin.core.component.inject

class LoginActivity : BaseActivity() {
    private lateinit var loginBinding: ActivityLoginBinding
    private lateinit var mobileNo: String
    private val loginViewModel: LoginViewModel by inject()

    override val layoutId: Int
        get() = R.layout.activity_login

    override fun onViewInit(binding: ViewDataBinding?) {
        loginBinding = binding as ActivityLoginBinding
        loginBinding.vm = loginViewModel
        validDataObserver()
        loginBinding.tvSignUpAccountBtn.setOnClickListener {
            launchActivity(RegisterActivity::class.java)
            finish()
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
                    launchActivity(VerificationActivity::class.java, "MobileNo", mobileNo)
                    Toast.makeText(this, it.data?.otp.toString(), Toast.LENGTH_LONG)
                        .show()
                    Log.d(
                        "LoginActivity",
                        "onViewInit: login data message ${it.data?.message}"
                    )
                }

                ApiResponse.Status.ERROR -> {
                    hideProgress()
                    showErrorMessage(this@LoginActivity, it.error?.message.toString())
                    Log.d("LoginActivity", " login error ${it.error?.message}")
                }

            }

        }
    }
    private fun validDataObserver() {
        loginViewModel.validationRegisterData.observe(this@LoginActivity, loginValidDataObserver)
    }

    private val loginValidDataObserver:Observer<ValidationState> by lazy {
        Observer {
            when(it.msg){
                ValidationResult.EMPTY_MOBILE_NUMBER, ValidationResult.VALID_MOBILE_NUMBER->{
                    loginBinding.etMobileOnLogin.error = getString(it.code)
                    loginBinding.etMobileOnLogin.requestFocus()
                }
                ValidationResult.SUCCESS->{
                    if (!Utility.isConnectionAvailable()) {
                        showErrorMessage(this@LoginActivity,
                            Constants.NetworkConstant.NO_INTERNET_AVAILABLE
                        )
                    }else{
                        mobileNo = loginBinding.etMobileOnLogin.text.toString()
                        val loginRequest = UserRequest().apply {
                            mobile = mobileNo
                        }
                        loginViewModel.hitLoginApi(loginRequest)
                        loginViewModel.getLoginResponse().observe(this@LoginActivity, loginObserver)
                    }
                }
                else->{}
            }

        }
    }

}