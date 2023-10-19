package com.stayhook.screen.verification

import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.View.OnKeyListener
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.ViewDataBinding
import com.stayhook.screen.dashboard.MainActivity
import com.stayhook.R
import com.stayhook.base.BaseActivity
import com.stayhook.databinding.ActivityVerificationBinding

class VerificationActivity : BaseActivity() {
    private lateinit var vBinding: ActivityVerificationBinding
    override val layoutId: Int
        get() = R.layout.activity_verification

    override fun onViewInit(binding: ViewDataBinding?) {
        vBinding = binding as ActivityVerificationBinding
        vBinding.vActivity = this@VerificationActivity
        vBinding.apply {
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
            btnVerifyOTP.setOnClickListener {
                launchActivity(MainActivity::class.java)
                finish()
            }
        }
        otpHandler()

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
                        vBinding.etTwo.requestFocus()
                        setOtpBackground(vBinding.etTwo)

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
                        vBinding.etThree.requestFocus()
                        setOtpBackground(vBinding.etThree)
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
                        vBinding.etFour.requestFocus()
                        setOtpBackground(vBinding.etFour)
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

}