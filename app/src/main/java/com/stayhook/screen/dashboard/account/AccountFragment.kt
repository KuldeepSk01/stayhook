package com.stayhook.screen.dashboard.account

import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.stayhook.R
import com.stayhook.base.BaseFragment
import com.stayhook.base.BaseResponse
import com.stayhook.databinding.FragmentAccountBinding
import com.stayhook.model.StringContentsModel
import com.stayhook.model.response.MyProfileResponse
import com.stayhook.network.ApiResponse
import com.stayhook.screen.dashboard.MainActivity
import com.stayhook.screen.dashboard.account.completeprofile.CompleteProfileActivity
import com.stayhook.screen.dashboard.account.downloadprofile.KYCActivity
import com.stayhook.screen.dashboard.account.editprofile.EditProfileActivity
import com.stayhook.screen.dashboard.account.helpcenter.HelpCenterFragment
import com.stayhook.screen.dashboard.account.mybooking.MyBookingActivity
import com.stayhook.screen.dashboard.account.mypayment.MyPaymentsActivity
import com.stayhook.screen.dashboard.account.myschedule.MyScheduledVisitActivity
import com.stayhook.screen.dashboard.account.myticket.MyTicketActivity
import com.stayhook.screen.dashboard.account.mytoken.MyTokenActivity
import com.stayhook.screen.dashboard.account.privacypolicy.PrivacyPolicyActivity
import com.stayhook.screen.login.LoginActivity
import com.stayhook.util.Constants
import com.stayhook.util.CustomDialogs
import org.koin.core.component.inject


class AccountFragment : BaseFragment() {
    private lateinit var accountBinding: FragmentAccountBinding
    private val mViewModel: AccountViewModel by inject()
    override fun getLayoutId(): Int {
        return R.layout.fragment_account
    }

    override fun onInitView(binding: ViewDataBinding, view: View) {
        accountBinding = binding as FragmentAccountBinding
        showTab()
        accountBinding.aFragment = this@AccountFragment
        accountBinding.apply {
            toolbarProfile.apply {
                ivToolBarBack.visibility = View.INVISIBLE
                tvToolBarTitle.text = getString(R.string.profile)
            }
        }

    }

    fun onClickEditProfile() {
        launchActivity(EditProfileActivity::class.java)
    }

    fun onClickMyScheduleVisit() {
        launchActivity(MyScheduledVisitActivity::class.java)

    }

    fun onClickMyBooking() {
        mPref.put(Constants.PreferenceConstant.IS_BACK_PRESS_TRUE,1)
        launchActivity(MyBookingActivity::class.java)
    }

    fun onClickMyToken() {
        launchActivity(MyTokenActivity::class.java)
    }

    fun onClickMyPayment() {
        launchActivity(MyPaymentsActivity::class.java)
    }

    fun onClickMyCompleteProfile() {
        launchActivity(CompleteProfileActivity::class.java)
    }

    fun onClickDownloadDocument() {
        //  hideTab()
        launchActivity(KYCActivity::class.java)

    }

    fun onClickResolveProblemTicket() {

        launchActivity(MyTicketActivity::class.java)


    }

    fun onClickHelpCenter() {
        replaceFragment(
            R.id.flMainContainer, HelpCenterFragment(), AccountFragment().javaClass.simpleName
        )
        hideTab()
    }

    fun onClickTermCondition() {
        val model= StringContentsModel().apply {
            title = Constants.StringContents.PRIVACY_POLICY
            desc = Constants.StringContents.PRIVACY_POLICY_DESC
        }
        val b = Bundle()
        b.putSerializable(Constants.DefaultConstants.MODEL_DETAIL,model)
        launchActivity(PrivacyPolicyActivity::class.java,Constants.DefaultConstants.BUNDLE,b)

    }

    fun onClickPrivacyPolicy() {
        val model= StringContentsModel().apply {
            title = Constants.StringContents.PRIVACY_POLICY
            desc = Constants.StringContents.PRIVACY_POLICY_DESC
        }
        val b = Bundle()
        b.putSerializable(Constants.DefaultConstants.MODEL_DETAIL,model)
        launchActivity(PrivacyPolicyActivity::class.java,Constants.DefaultConstants.BUNDLE,b)

    }


    fun onClickRefundPolicy() {
        val model= StringContentsModel().apply {
            title = Constants.StringContents.REFUND_POLICY
            desc = Constants.StringContents.REFUND_POLICY_DESC
        }
        val b = Bundle()
        b.putSerializable(Constants.DefaultConstants.MODEL_DETAIL,model)
        launchActivity(PrivacyPolicyActivity::class.java,Constants.DefaultConstants.BUNDLE,b)

    }

    fun onClickCancellationPolicy() {
        val model= StringContentsModel().apply {
            title = Constants.StringContents.CANCELLATION_POLICY
            desc = Constants.StringContents.CANCELLATION_POLICY_DESC
        }
        val b = Bundle()
        b.putSerializable(Constants.DefaultConstants.MODEL_DETAIL,model)
        launchActivity(PrivacyPolicyActivity::class.java,Constants.DefaultConstants.BUNDLE,b)

    }
    fun onClickAboutUs(){
        val model= StringContentsModel().apply {
            title = Constants.StringContents.ABOUT_US
            desc = Constants.StringContents.ABOUT_US_DESC
        }
        val b = Bundle()
        b.putSerializable(Constants.DefaultConstants.MODEL_DETAIL,model)
        launchActivity(PrivacyPolicyActivity::class.java,Constants.DefaultConstants.BUNDLE,b)

    }

    fun onClickDisclaimer(){
        val model= StringContentsModel().apply {
            title = Constants.StringContents.DISCLAIMER
            desc = Constants.StringContents.DISCLAIMER_DESC
        }
        val b = Bundle()
        b.putSerializable(Constants.DefaultConstants.MODEL_DETAIL,model)
        launchActivity(PrivacyPolicyActivity::class.java,Constants.DefaultConstants.BUNDLE,b)

    }

    fun onClickLogout() {
        launchActivity(LoginActivity::class.java)
        baseActivity.finish()
        mPref.clearSharedPref()
    }

    private fun hitMyProfileApi() {
        mViewModel.hitMyProfileApi()
        mViewModel.getMyProfileResponse().observe(requireActivity(), myProfileObserver)
    }


    private val myProfileObserver: Observer<ApiResponse<BaseResponse<MyProfileResponse>>> by lazy {
        Observer {
            when (it.status) {
                ApiResponse.Status.LOADING -> {
                    showProgress()
                }

                ApiResponse.Status.SUCCESS -> {
                    hideProgress()
                    mPref.setUserDetail(it.data?.data!!)

                    it.data.data.fullName.let { fName ->
                        accountBinding.usernameTV.text = fName
                    }
                    accountBinding.profilePostIV.apply {
                        if (it.data.data.profile.isEmpty()) {
                            this.background = ResourcesCompat.getDrawable(
                                resources, R.drawable.place_holder, null
                            )
                        } else {
                            Glide.with(requireActivity()).load(it.data.data.profile).into(this)
                        }
                    }
                }

                ApiResponse.Status.ERROR -> {
                    hideProgress()
                    CustomDialogs.showErrorMessage(requireActivity(), it.error?.message.toString())
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        hitMyProfileApi()

    }


}