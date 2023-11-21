package com.stayhook.screen.dashboard.home.recommondationdetail.writeareview

import androidx.databinding.ViewDataBinding
import com.stayhook.R
import com.stayhook.base.BaseActivity
import com.stayhook.databinding.ActivityWriteAReviewBinding


class WriteAReviewActivity : BaseActivity() {

    private lateinit var wBinding: ActivityWriteAReviewBinding
    override val layoutId: Int
        get() = R.layout.activity_write_a_review

    override fun onViewInit(binding: ViewDataBinding?) {
        wBinding = binding as ActivityWriteAReviewBinding
        wBinding.apply {
            toolbarWriteAReview.apply {
                ivToolBarBack.setOnClickListener {
                    onBackPressedDispatcher.onBackPressed()
                }
                tvToolBarTitle.text = getString(R.string.write_a_review)
            }
            submitBT.setOnClickListener {
                finish()
                //onBackPressedDispatcher.onBackPressed()
            }
        }
    }
}