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

class MyPaymentsFragment : BaseFragment() {

    private lateinit var mPBinding: FragmentMyPaymentsBinding

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

                adapter = MyPaymentAdapter(getRecommendationList(), requireContext())


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

    private fun getRecommendationList(): MutableList<Recommendation> {
        val list = mutableListOf<Recommendation>()
        list.add(
            Recommendation(
                "https://images.oyoroomscdn.com/uploads/hotel_image/168652/c4f2cc00b8fb02ba.jpg",
                "Abok hook",
                "single Room",
                "Noida",
                "2222",
                "4.5"
            )
        )
        list.add(
            Recommendation(
                "https://i0.wp.com/stanzaliving.wpcomstaging.com/wp-content/uploads/2022/04/d601b-hostels-vs-pgs-min.jpg?fit=4000%2C3000&ssl=1",
                "Kuldeep's hotel",
                "single Room",
                "Noida",
                "2222",
                "4.5"
            )
        )
        list.add(
            Recommendation(
                "https://content3.jdmagicbox.com/comp/chennai/g8/044pxx44.xx44.180125140725.t6g8/catalogue/victorias-in-ladies-and-pg-hostel-sirucheri-chennai-hostel-for-girl-students-c72xw2x314.jpg?clr=",
                "Raju's",
                "single Room",
                "Noida",
                "2222",
                "4.5"
            )
        )
        list.add(
            Recommendation(
                "https://www.thehivehostels.com/uploads/images/1658301040_7796f3aa4d7819a2f5d5.jpeg",
                "Deep's",
                "single Room",
                "Noida",
                "2222",
                "4.5"
            )
        )
        return list
    }


}