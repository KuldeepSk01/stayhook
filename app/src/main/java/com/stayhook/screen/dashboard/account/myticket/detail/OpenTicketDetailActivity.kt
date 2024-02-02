package com.stayhook.screen.dashboard.account.myticket.detail

import androidx.databinding.ViewDataBinding
import com.stayhook.R
import com.stayhook.base.BaseActivity
import com.stayhook.databinding.ActivityOpenTicketDetailBinding

class OpenTicketDetailActivity : BaseActivity() {
    private lateinit var mBind:ActivityOpenTicketDetailBinding


    override val layoutId: Int
        get() = R.layout.activity_open_ticket_detail

    override fun onViewInit(binding: ViewDataBinding?) {
        mBind = binding as ActivityOpenTicketDetailBinding
        mBind.apply {
            toolbarOpenTicketDetail.apply {
                ivToolBarBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
                tvToolBarTitle.text = getString(R.string.open)
            }
        }


    }
}