package com.stayhook.screen.dashboard.home.recommondationdetail.bookapartment

import android.util.Log
import android.view.View
import androidx.databinding.ViewDataBinding
import com.stayhook.R
import com.stayhook.base.BaseFragment
import com.stayhook.databinding.FragmentSelectDateBinding
import com.stayhook.databinding.FragmentSummaryBookBinding

class SummaryBookFragment : BaseFragment() {

    private lateinit var sbBinding : FragmentSummaryBookBinding
    private val paymentFragment : PaymentFragment by lazy {
        PaymentFragment()
    }
    override fun getLayoutId(): Int {
        return R.layout.fragment_summary_book
    }

    override fun onInitView(binding: ViewDataBinding, view: View) {
        sbBinding = binding as FragmentSummaryBookBinding
        sbBinding.apply {
            toolbarSumary.apply {
                ivToolBarBack.setOnClickListener {
                    onBackPress()
                }
                tvToolBarTitle.text = getString(R.string.summary)
            }
            btnPaySummary.setOnClickListener {
                replaceFragment(R.id.flMainContainer,paymentFragment,this@SummaryBookFragment.javaClass.simpleName)
                Log.d("RDFragment", "onInitView RDFragment: className ${this@SummaryBookFragment.javaClass.simpleName}")
            }
        }
    }

}