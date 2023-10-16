package com.stayhook.screen.dashboard.account.mybooking

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.ViewDataBinding
import com.stayhook.R
import com.stayhook.base.BaseFragment
import com.stayhook.databinding.FragmentMyBookingBinding
import com.stayhook.screen.dashboard.account.AccountFragment
import com.stayhook.screen.dashboard.account.completeprofile.CompleteProfileFragment
import com.stayhook.screen.dashboard.account.mybooking.moveout.MovedOutFragment

class MyBookingFragment : BaseFragment() {
    private lateinit var mBinding: FragmentMyBookingBinding
    override fun getLayoutId(): Int {
        return R.layout.fragment_my_booking
    }

    override fun onInitView(binding: ViewDataBinding, view: View) {
        mBinding = binding as FragmentMyBookingBinding
        setBackGround(mBinding.tvCurrentMB)

        mBinding.apply {
            toolBarMyBooking.apply {
                ivToolBarBack.visibility = View.GONE

            }


            tvCurrentMB.setOnClickListener {
                setBackGround(it as AppCompatTextView)
            }
            tvPastMB.setOnClickListener {
                setBackGround(it as AppCompatTextView)
            }
            tvUpcomingMB.setOnClickListener {
                setBackGround(it as AppCompatTextView)
            }

            moveOutBT.setOnClickListener {
                replaceFragment(R.id.flMainContainer, MovedOutFragment(), MyBookingFragment().javaClass.simpleName)
            }
        }
    }
    private fun setBackGround(
        tv1: AppCompatTextView,
    ) {
        mBinding.apply {
            tvCurrentMB.background =
                ResourcesCompat.getDrawable(resources, R.drawable.otp_box_background, null)
            tvPastMB.background =
                ResourcesCompat.getDrawable(resources, R.drawable.otp_box_background, null)
            tvUpcomingMB.background =
                ResourcesCompat.getDrawable(resources, R.drawable.otp_box_background, null)
            tvCurrentMB.setTextColor(resources.getColor(R.color.sub_heading_text_color, null))
            tvPastMB.setTextColor(resources.getColor(R.color.sub_heading_text_color, null))
            tvUpcomingMB.setTextColor(resources.getColor(R.color.sub_heading_text_color, null))

        }
        tv1.setTextColor(resources.getColor(R.color.primary_color, null))
        tv1.background =
            ResourcesCompat.getDrawable(resources, R.drawable.select_bed_filter_bg, null)
    }

}