package com.stayhook.screen.dashboard.account.mypayment

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.stayhook.R
import com.stayhook.adapter.MyPaymentAdapter
import com.stayhook.base.BaseFragment
import com.stayhook.databinding.FragmentMyPaymentsBinding
import com.stayhook.model.Recommendation
import com.stayhook.screen.dashboard.home.HomeViewModel
import org.koin.core.component.inject

class MyPaymentsFragment : BaseFragment() {

    private lateinit var mPBinding: FragmentMyPaymentsBinding
    private val hVM:HomeViewModel by inject()

    override fun getLayoutId(): Int {
        return R.layout.fragment_my_payments
    }

    override fun onInitView(binding: ViewDataBinding, view: View) {
        mPBinding = binding as FragmentMyPaymentsBinding
        setBackGround(mPBinding.tvPendingPaymentMP)

        mPBinding.apply {
            toolbarMyPayment.apply {
                ivToolBarBack.setOnClickListener {
                    onBackPress()
                }
                tvToolBarTitle.text = getString(R.string.my_payment_text)
            }

            tvPendingPaymentMP.setOnClickListener {
                setBackGround(it as AppCompatTextView)
            }
            tvLastPaymentMP.setOnClickListener {
                setBackGround(it as AppCompatTextView)
            }

            rvMyPayment.apply {
                itemAnimator = DefaultItemAnimator()
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

                adapter = MyPaymentAdapter(hVM.getRecommendationList(), requireContext())


            }


        }
    }

    private fun setBackGround(
        tv1: AppCompatTextView,
    ) {
        mPBinding.apply {
            tvPendingPaymentMP.background =
                ResourcesCompat.getDrawable(resources, R.drawable.otp_box_background, null)
            tvLastPaymentMP.background =
                ResourcesCompat.getDrawable(resources, R.drawable.otp_box_background, null)
            tvPendingPaymentMP.setTextColor(
                resources.getColor(
                    R.color.sub_heading_text_color,
                    null
                )
            )
            tvLastPaymentMP.setTextColor(resources.getColor(R.color.sub_heading_text_color, null))

        }
        tv1.setTextColor(resources.getColor(R.color.primary_color, null))
        tv1.background =
            ResourcesCompat.getDrawable(resources, R.drawable.select_bed_filter_bg, null)
    }



}