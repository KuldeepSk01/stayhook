package com.stayhook.screen.verification

import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.View.OnKeyListener
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.stayhook.R
import com.stayhook.base.BaseActivity
import com.stayhook.base.BaseResponse
import com.stayhook.databinding.ActivityVerificationBinding
import com.stayhook.model.request.UserRequest
import com.stayhook.model.response.OTPResponse
import com.stayhook.model.response.UserResponse
import com.stayhook.network.ApiResponse
import com.stayhook.screen.dashboard.MainActivity
import com.stayhook.util.Constants.PreferenceConstant.IS_LOGIN
import com.stayhook.util.Constants.PreferenceConstant.TOKEN
import com.stayhook.util.CustomDialogs
import org.koin.core.component.inject

class VerificationActivity : BaseActivity() {
    private lateinit var vBinding: ActivityVerificationBinding
    private val mViewModel: VerificationViewModel by inject()
    private lateinit var countDownTimer: CountDownTimer
    private lateinit var handler: Handler


    companion object {
        const val TAG = "VerificationActivity"
    }

    override val layoutId: Int
        get() = R.layout.activity_verification

    override fun onViewInit(binding: ViewDataBinding?) {
        vBinding = binding as ActivityVerificationBinding
        val mobileNo = intent.getStringExtra("MobileNo")

        vBinding.vActivity = this@VerificationActivity
        handler = Handler(Looper.getMainLooper())
        otpTimer()
        vBinding.apply {

            tvOtpSent.text = "Otp has been sent on your \n +91 $mobileNo mobile number."
            toolbarAV.apply {
                ivToolBarBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
                tvToolBarTitle.visibility = View.GONE
                ivToolBarRightIcon.visibility = View.GONE
            }
            etOne.setOnClickListener {
                isFocused(etOne.isFocusable, etOne)
            }
            etTwo.setOnClickListener {
                isFocused(etTwo.isFocusable, etTwo)
            }
            etThree.setOnClickListener {
                isFocused(etThree.isFocusable, etThree)
            }
            etFour.setOnClickListener {
                isFocused(etFour.isFocusable, etFour)
            }

            tvResend.setOnClickListener {
                val request = UserRequest().apply {
                    mobile = mobileNo
                }
                mViewModel.hitResendOTPApi(request)
                mViewModel.getResendOTPResponse()
                    .observe(this@VerificationActivity, resendOtpObserver)
            }
            btnVerifyOTP.setOnClickListener {
                stopCountDownTime()
                val code = getOtpCode()
                if (code.length != 4) {
                    Toast.makeText(
                        this@VerificationActivity, "Please enter code.", Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val request = UserRequest().apply {
                        mobile = mobileNo
                        userotp = code
                    }
                    Log.d(TAG, "onViewInit: $request")
                    mViewModel.hitVerifyOTPApi(request)
                    mViewModel.getVerifyOTPResponse()
                        .observe(this@VerificationActivity, verifyObserver)
                }


            }

        }
        otpHandler()

    }

    private fun otpTimer() {
        countDownTimer = object : CountDownTimer(60000, 1000) {
            override fun onTick(p0: Long) {
                vBinding.apply {
                    val second = p0 / 1000
                    tvResend.isClickable = false
                    tvResend.setTextColor(
                        ResourcesCompat.getColor(
                            resources, R.color.text_hint_color, null
                        )
                    )
                    tvOTPTime.let {
                        it.setTextColor(
                            ResourcesCompat.getColor(
                                resources, R.color.primary_color, null
                            )
                        )
                        if (second > 11) {
                            it.text = "00:$second"
                        } else {
                            it.text = "00:0$second"
                        }
                    }


                }
            }

            override fun onFinish() {
                vBinding.apply {
                    tvOTPTime.apply {
                        setTextColor(
                            ResourcesCompat.getColor(
                                resources, R.color.text_hint_color, null
                            )
                        )
                        text = "00:00"
                    }
                    tvResend.isClickable = true
                    tvResend.setTextColor(
                        ResourcesCompat.getColor(
                            resources, R.color.primary_color, null
                        )
                    )
                }
            }

        }
        countDownTimer.start()
    }

    private val verifyObserver: Observer<ApiResponse<BaseResponse<UserResponse>>> by lazy {
        Observer {
            when (it.status) {
                ApiResponse.Status.LOADING -> {
                    showProgress()
                }

                ApiResponse.Status.SUCCESS -> {
                    hideProgress()
                    mPref.put(IS_LOGIN, true)
                    mPref.put(TOKEN, it.data?.data?.token!!)
                    launchActivity(MainActivity::class.java)
                    finish()
                }

                ApiResponse.Status.ERROR -> {
                    hideProgress()
                    CustomDialogs.showErrorMessage(
                        this@VerificationActivity, it.error?.message.toString()
                    )
                }

            }

        }
    }
    private val resendOtpObserver: Observer<ApiResponse<OTPResponse>> by lazy {
        Observer {
            when (it.status) {
                ApiResponse.Status.LOADING -> {
                    Log.d("LoginActivity", "onLoading: login loading...")
                    showProgress()
                }

                ApiResponse.Status.SUCCESS -> {
                    hideProgress()
                    CustomDialogs.showSuccessMessage(
                        this@VerificationActivity, it.data?.message.toString()
                    )
                    otpTimer()
                }

                ApiResponse.Status.ERROR -> {
                    hideProgress()
                    CustomDialogs.showErrorMessage(
                        this@VerificationActivity, it.error?.message.toString()
                    )
                    Log.d("LoginActivity", " login error ${it.error?.message}")
                }
            }

        }
    }

    private fun getOtpCode(): String {
        vBinding.apply {
            return "${etOne.text}${etTwo.text}${etThree.text}${etFour.text}"
        }
    }

    private fun isFocused(isFocus: Boolean, v: View) {
        if (isFocus) {
            setOnOtpETClick(v)
        }
    }

    private fun otpHandler() {
        vBinding.apply {
            etOne.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    if (p0?.length == 1) {
                        etTwo.requestFocus()
                        setOtpBackground(etTwo)

                    }
                }

            })
            etTwo.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    if (p0?.length == 1) {
                        etThree.requestFocus()
                        setOtpBackground(etThree)
                    }
                }

            })
            etThree.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    if (p0?.length == 1) {
                        etFour.requestFocus()
                        setOtpBackground(etFour)
                    }
                }

            })
            etFour.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    if (p0?.length == 1) {
                        vBinding.etFour.requestFocus()
                    }
                }

            })

            etTwo.setOnKeyListener(object : OnKeyListener {
                override fun onKey(p0: View?, p1: Int, p2: KeyEvent?): Boolean {
                    if (p1 == KeyEvent.KEYCODE_DEL && p2?.action == KeyEvent.ACTION_DOWN) {
                        return if (vBinding.etTwo.text.toString().trim().isEmpty()) {
                            vBinding.etOne.setSelection(vBinding.etOne.text?.length!!)
                            vBinding.etOne.requestFocus()
                            setOtpBackground(vBinding.etOne)
                            true
                        } else false
                    }
                    return false
                }

            })
            etThree.setOnKeyListener(object : OnKeyListener {
                override fun onKey(p0: View?, p1: Int, p2: KeyEvent?): Boolean {
                    if (p1 == KeyEvent.KEYCODE_DEL && p2?.action == KeyEvent.ACTION_DOWN) {
                        return if (vBinding.etThree.text.toString().trim().isEmpty()) {
                            vBinding.etTwo.setSelection(vBinding.etOne.text?.length!!)
                            vBinding.etTwo.requestFocus()
                            setOtpBackground(vBinding.etTwo)
                            true
                        } else false
                    }
                    return false
                }
            })

            etFour.setOnKeyListener(object : OnKeyListener {
                override fun onKey(p0: View?, p1: Int, p2: KeyEvent?): Boolean {
                    if (p1 == KeyEvent.KEYCODE_DEL && p2?.action == KeyEvent.ACTION_DOWN) {
                        return if (vBinding.etFour.text.toString().trim().isEmpty()) {
                            vBinding.etThree.setSelection(vBinding.etOne.text?.length!!)
                            vBinding.etThree.requestFocus()
                            setOtpBackground(vBinding.etThree)
                            true
                        } else false
                    }
                    return false
                }
            })
        }


    }

    private fun setOtpBackground(etTwo: AppCompatEditText) {
        vBinding.etOne.background =
            ResourcesCompat.getDrawable(resources, R.drawable.otp_box_background, null)
        vBinding.etTwo.background =
            ResourcesCompat.getDrawable(resources, R.drawable.otp_box_background, null)
        vBinding.etThree.background =
            ResourcesCompat.getDrawable(resources, R.drawable.otp_box_background, null)
        vBinding.etFour.background =
            ResourcesCompat.getDrawable(resources, R.drawable.otp_box_background, null)
        etTwo.background =
            ResourcesCompat.getDrawable(resources, R.drawable.otp_box_outline_drawable, null)


    }

    fun setOnOtpETClick(v: View) {
        setOtpBackground(v as AppCompatEditText)
    }


    private fun stopCountDownTime() {
        countDownTimer.cancel()
        countDownTimer.onFinish()

    }
}