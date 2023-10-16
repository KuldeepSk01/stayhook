package com.stayhook.screen.dashboard.account.helpcenter

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.stayhook.R
import com.stayhook.adapter.HelpCenterAdapter
import com.stayhook.base.BaseFragment
import com.stayhook.databinding.FragmentHelpCenterBinding
import com.stayhook.model.HelpCenter

class HelpCenterFragment : BaseFragment() {

    private lateinit var hBinding: FragmentHelpCenterBinding
    override fun getLayoutId(): Int {
        return R.layout.fragment_help_center
    }

    override fun onInitView(binding: ViewDataBinding, view: View) {
        hBinding = binding as FragmentHelpCenterBinding
        setBackGround(hBinding.tvReservationHC)

        hBinding.apply {
            toolbarHelpCenter.apply {
                ivToolBarBack.setOnClickListener {
                    onBackPress()
                }
                tvToolBarTitle.text = getString(R.string.help_center_text)
            }
            rvHelpCenter.apply {
                itemAnimator = DefaultItemAnimator()
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = HelpCenterAdapter(getHelpCenterList(), requireContext())
            }




            tvReservationHC.setOnClickListener {
                setBackGround(it as AppCompatTextView)
            }
            tvPaymentsHC.setOnClickListener {
                setBackGround(it as AppCompatTextView)
            }
            tvCouponsHC.setOnClickListener {
                setBackGround(it as AppCompatTextView)
            }
        }
    }

    private fun getHelpCenterList(): MutableList<HelpCenter> {
        val list = mutableListOf<HelpCenter>()
        list.add(
            HelpCenter(
                getString(R.string.what_methods_of_payment_does_homeline_accept_text),
                getString(R.string.lorem_ipsum_dolor_sit_amet_consectetur_adipiscing_elit_ut_aliquam_purus_sit_amet_luctus_venenatis_lectus_magna_fringilla_urna_porttitor_text)
            )
        )
        list.add(
            HelpCenter(
                getString(R.string.what_methods_of_payment_does_homeline_accept_text),
                getString(R.string.lorem_ipsum_dolor_sit_amet_consectetur_adipiscing_elit_ut_aliquam_purus_sit_amet_luctus_venenatis_lectus_magna_fringilla_urna_porttitor_text)
            )
        )
        list.add(
            HelpCenter(
                getString(R.string.what_methods_of_payment_does_homeline_accept_text),
                getString(R.string.lorem_ipsum_dolor_sit_amet_consectetur_adipiscing_elit_ut_aliquam_purus_sit_amet_luctus_venenatis_lectus_magna_fringilla_urna_porttitor_text)
            )
        )
        list.add(
            HelpCenter(
                getString(R.string.what_methods_of_payment_does_homeline_accept_text),
                getString(R.string.lorem_ipsum_dolor_sit_amet_consectetur_adipiscing_elit_ut_aliquam_purus_sit_amet_luctus_venenatis_lectus_magna_fringilla_urna_porttitor_text)
            )
        )

        return list
    }

    private fun setBackGround(
        tv1: AppCompatTextView,
    ) {
        hBinding.apply {
            tvReservationHC.background =
                ResourcesCompat.getDrawable(resources, R.drawable.otp_box_background, null)
            tvPaymentsHC.background =
                ResourcesCompat.getDrawable(resources, R.drawable.otp_box_background, null)
            tvCouponsHC.background =
                ResourcesCompat.getDrawable(resources, R.drawable.otp_box_background, null)
            tvReservationHC.setTextColor(resources.getColor(R.color.sub_heading_text_color, null))
            tvPaymentsHC.setTextColor(resources.getColor(R.color.sub_heading_text_color, null))
            tvCouponsHC.setTextColor(resources.getColor(R.color.sub_heading_text_color, null))

        }
        tv1.setTextColor(resources.getColor(R.color.primary_color, null))
        tv1.background =
            ResourcesCompat.getDrawable(resources, R.drawable.select_bed_filter_bg, null)
    }

}