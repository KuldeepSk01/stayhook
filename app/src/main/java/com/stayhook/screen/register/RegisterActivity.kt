package com.stayhook.screen.register

import androidx.databinding.ViewDataBinding
import com.stayhook.MainActivity
import com.stayhook.R
import com.stayhook.base.BaseActivity
import com.stayhook.databinding.ActivityRegisterBinding
import com.stayhook.screen.login.LoginActivity

class RegisterActivity : BaseActivity() {
    private lateinit var rBinding: ActivityRegisterBinding

    override val layoutId: Int
        get() = R.layout.activity_register

    override fun onViewInit(binding: ViewDataBinding?) {
        rBinding = binding as ActivityRegisterBinding

        rBinding.ivBackRegister.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        rBinding.signupBtn.setOnClickListener {
            launchActivity(MainActivity::class.java)
            finish()
        }
        rBinding.tvLoginTXTBtn.setOnClickListener {
            launchActivity(LoginActivity::class.java)
            finish()
        }
    }
}