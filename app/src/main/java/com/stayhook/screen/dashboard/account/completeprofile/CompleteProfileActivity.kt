package com.stayhook.screen.dashboard.account.completeprofile

import android.app.Dialog
import androidx.databinding.ViewDataBinding
import com.stayhook.R
import com.stayhook.base.BaseActivity
import com.stayhook.databinding.ActivityCompleteProfileBinding
import com.stayhook.util.CustomDialogs

class CompleteProfileActivity : BaseActivity() {
    private lateinit var cBinding: ActivityCompleteProfileBinding
    override val layoutId: Int
        get() = R.layout.activity_complete_profile

    override fun onViewInit(binding: ViewDataBinding?) {
        cBinding = binding as ActivityCompleteProfileBinding
        cBinding.apply {
            toolbarCompleteProfile.apply {
                ivToolBarBack.setOnClickListener {
                    onBackPressedDispatcher.onBackPressed()
                }
                tvToolBarTitle.text = getString(R.string.complete_profile_text)
            }


            btnSaveProfile.setOnClickListener {
                CustomDialogs.showCustomSuccessDialog(this@CompleteProfileActivity,
                    getString(R.string.verification_complete_text),
                    getString(R.string.verification_our_representative_will_text),
                    getString(R.string.title_okay),
                    object : CustomDialogs.CustomDialogsListener {
                        override fun onComplete(d: Dialog) {
                            d.dismiss()
                            finish()
                        }
                    }).show()
            }
        }
    }


}