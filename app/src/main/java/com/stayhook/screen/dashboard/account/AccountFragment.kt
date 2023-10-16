package com.stayhook.screen.dashboard.account

import android.view.View
import androidx.databinding.ViewDataBinding
import com.stayhook.R
import com.stayhook.base.BaseFragment
import com.stayhook.databinding.FragmentAccountBinding
import com.stayhook.preference.PreferenceHelper
import com.stayhook.screen.dashboard.account.completeprofile.CompleteProfileFragment
import com.stayhook.screen.dashboard.account.editprofile.EditProfileFragment
import com.stayhook.screen.dashboard.account.helpcenter.HelpCenterFragment
import com.stayhook.screen.dashboard.account.mybooking.MyBookingFragment
import com.stayhook.screen.dashboard.account.mypayment.MyPaymentsFragment
import com.stayhook.screen.dashboard.account.myticket.MyTicketFragment
import com.stayhook.screen.login.LoginActivity
import org.koin.core.component.inject


class AccountFragment : BaseFragment() {
    private lateinit var accountBinding: FragmentAccountBinding
    private val mPref: PreferenceHelper by inject()
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
        replaceFragment(
            R.id.flMainContainer,
            EditProfileFragment(),
            AccountFragment().javaClass.simpleName
        )
        hideTab()


    }

    fun onClickMyBooking() {
        replaceFragment(
            R.id.flMainContainer,
            MyBookingFragment(),
            AccountFragment().javaClass.simpleName
        )
        hideTab()


    }

    fun onClickMyPayment() {
        replaceFragment(
            R.id.flMainContainer,
            MyPaymentsFragment(),
            AccountFragment().javaClass.simpleName
        )
        hideTab()


    }

    fun onClickMyCompleteProfile() {
        replaceFragment(
            R.id.flMainContainer,
            CompleteProfileFragment(),
            AccountFragment().javaClass.simpleName
        )
        hideTab()

    }

    fun onClickDownloadDocument() {
        //  hideTab()

    }

    fun onClickResolveProblemTicket() {
        replaceFragment(
            R.id.flMainContainer,
            MyTicketFragment(),
            AccountFragment().javaClass.simpleName
        )
        hideTab()

    }

    fun onClickHelpCenter() {
        replaceFragment(
            R.id.flMainContainer,
            HelpCenterFragment(),
            AccountFragment().javaClass.simpleName
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

}