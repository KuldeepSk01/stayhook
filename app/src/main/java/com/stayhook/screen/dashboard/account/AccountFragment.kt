package com.stayhook.screen.dashboard.account

import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.stayhook.R
import com.stayhook.base.BaseFragment
import com.stayhook.base.BaseResponse
import com.stayhook.databinding.FragmentAccountBinding
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
import com.stayhook.screen.login.LoginActivity
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
                ivToolBarBack.visibility = View.GONE
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
        launchActivity(MyBookingActivity::class.java)
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
       /* replaceFragment(
            R.id.flMainContainer, MyTicketFragment(), AccountFragment().javaClass.simpleName
        )
        hideTab()*/

    }

    fun onClickHelpCenter() {
        replaceFragment(
            R.id.flMainContainer, HelpCenterFragment(), AccountFragment().javaClass.simpleName
        )
        hideTab()
    }

    fun onClickTermCondition() {
        // hideTab()

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