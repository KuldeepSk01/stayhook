package com.stayhook.screen.dashboard.account.completeprofile

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.stayhook.R
import com.stayhook.base.BaseActivity
import com.stayhook.databinding.ActivityCompleteProfileBinding
import com.stayhook.model.request.UserRequest
import com.stayhook.model.response.MyProfileResponse
import com.stayhook.model.response.UserResponse
import com.stayhook.util.Constants
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
        }

    }


    override fun onResume() {
        super.onResume()
       val  mProfile = mPref.getUserDetail()
        Log.d("TAG", "onResume: Complete Profile $mProfile")
        cBinding.apply {
        mProfile.let {
            etFullNameCP.text = it.fullName
            etDOBCP.text = it.dob
            etGenderCP.text = it.gender
            etPANNumberCP.text = it.panNumber
            etAadhaarNumberCP.text = it.aadhaarNumber
            etAddressCP.text = it.address
            etStateCP.text = it.stateName
            etCityCP.text = it.cityName

            Glide.with(this@CompleteProfileActivity).load(it.uploadAadhaar).into(ivUploadFrontAadhaarImageCP)
            Glide.with(this@CompleteProfileActivity).load(it.uploadAadhaar2).into(ivUploadedBackAadhaarImageCP)
            Glide.with(this@CompleteProfileActivity).load(it.panNumber).into(ivUploadedPanImageCP)
            Glide.with(this@CompleteProfileActivity).load(it.policeVerification).into(ivUploadedPoliceVerificationImageCP)

        }
        }

    }


}