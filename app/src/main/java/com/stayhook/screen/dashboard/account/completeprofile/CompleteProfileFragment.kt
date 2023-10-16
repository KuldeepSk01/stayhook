package com.stayhook.screen.dashboard.account.completeprofile

import android.app.Dialog
import android.view.View
import androidx.databinding.ViewDataBinding
import com.stayhook.R
import com.stayhook.base.BaseFragment
import com.stayhook.databinding.FragmentCompleteProfileBinding
import com.stayhook.util.CustomDialogs

class CompleteProfileFragment : BaseFragment() {
    private lateinit var cBinding: FragmentCompleteProfileBinding
    override fun getLayoutId(): Int {
        return R.layout.fragment_complete_profile
    }

    override fun onInitView(binding: ViewDataBinding, view: View) {
        cBinding = binding as FragmentCompleteProfileBinding
        cBinding.apply {
            toolbarCompleteProfile.apply {
                ivToolBarBack.setOnClickListener {
                    onBackPress()
                }
                tvToolBarTitle.text = getString(R.string.complete_profile_text)
            }


            btnSaveProfile.setOnClickListener {
                CustomDialogs.showCustomSuccessDialog(requireContext(),
                    getString(R.string.verification_complete_text),
                    getString(R.string.verification_our_representative_will_text),
                    getString(R.string.title_okay),
                    object : CustomDialogs.CustomDialogsListener {
                        override fun onComplete(d: Dialog) {
                            d.dismiss()
                            onBackPress()
                        }
                    }).show()
            }
        }
    }

}