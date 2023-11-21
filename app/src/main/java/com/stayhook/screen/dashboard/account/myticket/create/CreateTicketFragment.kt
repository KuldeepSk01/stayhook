package com.stayhook.screen.dashboard.account.myticket.create

import android.app.Dialog
import android.view.View
import androidx.databinding.ViewDataBinding
import com.stayhook.R
import com.stayhook.base.BaseFragment
import com.stayhook.databinding.FragmentCreateTicketBinding
import com.stayhook.util.CustomDialogs

class CreateTicketFragment : BaseFragment() {
    private lateinit var cBinding: FragmentCreateTicketBinding

    override fun getLayoutId(): Int {
        return R.layout.fragment_create_ticket
    }

    override fun onInitView(binding: ViewDataBinding, view: View) {
        cBinding = binding as FragmentCreateTicketBinding
        cBinding.apply {
            toolbarHelpCenter.apply {
                ivToolBarBack.setOnClickListener {
                    onBackPress()
                }
            }
            btnNewTicket.setOnClickListener {
                CustomDialogs.showCustomSuccessDialog(baseActivity.baseContext,
                    getString(R.string.ticket_created_successfully_text),
                    getString(R.string.ticket_created_successfully_text),
                    getString(R.string.new_ticket_text),
                    object : CustomDialogs.CustomDialogsListener {
                        override fun onComplete(d: Dialog) {
                            d.dismiss()
                            onBackPress()
                        }
                    }).show()
            }
        }
    }

}