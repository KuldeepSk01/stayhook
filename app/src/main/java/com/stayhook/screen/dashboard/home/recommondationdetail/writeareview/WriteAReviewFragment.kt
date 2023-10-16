package com.stayhook.screen.dashboard.home.recommondationdetail.writeareview

import android.view.View
import androidx.databinding.ViewDataBinding
import com.stayhook.R
import com.stayhook.base.BaseFragment
import com.stayhook.databinding.FragmentWriteAReviewBinding


class WriteAReviewFragment : BaseFragment() {

    private lateinit var wBinding: FragmentWriteAReviewBinding

    override fun getLayoutId(): Int {
        return R.layout.fragment_write_a_review
    }

    override fun onInitView(binding: ViewDataBinding, view: View) {
        wBinding = binding as FragmentWriteAReviewBinding
        wBinding.apply {
            toolbarWriteAReview.apply {
                ivToolBarBack.setOnClickListener {
                    onBackPress()
                }
                tvToolBarTitle.text = getString(R.string.write_a_review)
            }
            submitBT.setOnClickListener {
                onBackPress()
            }
        }
    }
}