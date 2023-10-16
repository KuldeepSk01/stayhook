package com.stayhook.screen.dashboard.account.editprofile

import android.view.View
import androidx.databinding.ViewDataBinding
import com.stayhook.R
import com.stayhook.base.BaseFragment
import com.stayhook.databinding.FragmentEditProfileBinding


class EditProfileFragment : BaseFragment() {

    private lateinit var pBinding: FragmentEditProfileBinding
    override fun getLayoutId(): Int {
        return R.layout.fragment_edit_profile
    }

    override fun onInitView(binding: ViewDataBinding, view: View) {
        pBinding = binding as FragmentEditProfileBinding
        pBinding.apply {
            toolbarEditProfile.apply {
                ivToolBarBack.setOnClickListener {
                    onBackPress()
                }
                tvToolBarTitle.text = getString(R.string.edit_profile)
            }
            btnUpdateProfile.setOnClickListener {
                onBackPress()
            }


        }
    }

}