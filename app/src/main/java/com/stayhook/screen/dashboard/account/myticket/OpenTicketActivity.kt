package com.stayhook.screen.dashboard.account.myticket

import android.widget.Toast
import androidx.databinding.ViewDataBinding
import com.stayhook.R
import com.stayhook.base.BaseActivity
import com.stayhook.databinding.ActivityOpenTicketBinding
import com.stayhook.model.Ticket
import com.stayhook.util.Constants
import com.stayhook.util.serializable

class OpenTicketActivity : BaseActivity() {
    private lateinit var mBind: ActivityOpenTicketBinding
    override val layoutId: Int
        get() = R.layout.activity_open_ticket

    override fun onViewInit(binding: ViewDataBinding?) {
        mBind = binding as ActivityOpenTicketBinding
        val model = intent.getBundleExtra(Constants.DefaultConstants.BUNDLE)
            ?.serializable<Ticket>(Constants.DefaultConstants.MODEL_DETAIL)
//        Toast.makeText(this@OpenTicketActivity, "model ${model.toString()}", Toast.LENGTH_SHORT)
//            .show()

        mBind.apply {
            toolbarOpenTicket.apply {
                tvToolBarTitle.text = "#${model?.id}"
                ivToolBarBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
            }
        }
    }
}