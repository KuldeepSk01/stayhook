package com.stayhook.screen.dashboard.account.privacypolicy

import androidx.databinding.ViewDataBinding
import com.stayhook.R
import com.stayhook.base.BaseActivity
import com.stayhook.databinding.ActivityPrivacyPolicyBinding
import com.stayhook.model.StringContentsModel
import com.stayhook.util.Constants
import com.stayhook.util.serializable

class PrivacyPolicyActivity : BaseActivity() {
    private lateinit var mBind: ActivityPrivacyPolicyBinding

    override val layoutId: Int
        get() = R.layout.activity_privacy_policy

    override fun onViewInit(binding: ViewDataBinding?) {
        mBind = binding as ActivityPrivacyPolicyBinding

        val model = intent.getBundleExtra(Constants.DefaultConstants.BUNDLE)
            ?.serializable<StringContentsModel>(Constants.DefaultConstants.MODEL_DETAIL)

        mBind.apply {
            toolbarPrivacyPolicy.apply {
                ivToolBarBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
                tvToolBarTitle.text = model?.title
            }
            tvDescription.text = model?.desc
        }
    }
}