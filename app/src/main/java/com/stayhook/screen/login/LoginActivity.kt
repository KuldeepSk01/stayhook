package com.stayhook.screen.login

import android.text.TextUtils
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import com.stayhook.R
import com.stayhook.base.BaseActivity
import com.stayhook.databinding.ActivityLoginBinding
import com.stayhook.preference.IS_LOGIN
import com.stayhook.preference.PreferenceHelper
import com.stayhook.screen.register.RegisterActivity
import com.stayhook.screen.verification.VerificationActivity
import org.koin.core.component.inject

class LoginActivity : BaseActivity() {
    private lateinit var loginBinding: ActivityLoginBinding
    private val mnPref: PreferenceHelper by inject()

    override val layoutId: Int
        get() = R.layout.activity_login

    override fun onViewInit(binding: ViewDataBinding?) {
        loginBinding = binding as ActivityLoginBinding
        loginBinding.tvSignUpAccountBtn.setOnClickListener {
            launchActivity(RegisterActivity::class.java)
            finish()
        }
        loginBinding.loginBtn.setOnClickListener {
            if (TextUtils.isEmpty(loginBinding.etMobileOnLogin.text.toString())) {
                Toast.makeText(this@LoginActivity, "Please enter mobileNo:", Toast.LENGTH_SHORT)
                    .show()
            } else {
                mnPref.put(IS_LOGIN,true)
                launchActivity(VerificationActivity::class.java)
                finish()
            }

        }
    }
}