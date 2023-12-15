package com.stayhook.screen.dashboard.account.myticket.create

import android.app.Dialog
import androidx.databinding.ViewDataBinding
import com.stayhook.R
import com.stayhook.base.BaseActivity
import com.stayhook.databinding.ActivityCreateTicketBinding
import com.stayhook.util.CustomDialogs

class CreateTicketActivity : BaseActivity() {
    private lateinit var cBinding: ActivityCreateTicketBinding

    override val layoutId: Int
        get() = R.layout.activity_create_ticket

    override fun onViewInit(binding: ViewDataBinding?) {
        cBinding = binding as ActivityCreateTicketBinding
        cBinding.apply {
            toolbarHelpCenter.apply {
                tvToolBarTitle.text = getString(R.string.create_ticket)
                ivToolBarBack.setOnClickListener {
                    onBackPressedDispatcher.onBackPressed()
                }
            }
            btnNewTicket.setOnClickListener {
                CustomDialogs.showCustomSuccessDialog(this@CreateTicketActivity,
                    getString(R.string.ticket_created_successfully_text),
                    getString(R.string.ticket_created_successfully_text),
                    getString(R.string.new_ticket_text),
                    object : CustomDialogs.CustomDialogsListener {
                        override fun onComplete(d: Dialog) {
                            d.dismiss()
                            onBackPressedDispatcher.onBackPressed()
                        }
                    }).show()

            }
        }
    }


}