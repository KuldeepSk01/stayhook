package com.stayhook.screen.dashboard.account.mybooking.moveout

import android.app.Dialog
import android.view.View
import androidx.databinding.ViewDataBinding
import com.stayhook.R
import com.stayhook.base.BaseFragment
import com.stayhook.databinding.FragmentMovedOutBinding
import com.stayhook.util.CustomDialogs

class MovedOutFragment : BaseFragment() {
    private lateinit var mBinding: FragmentMovedOutBinding
    override fun getLayoutId(): Int {
        return R.layout.fragment_moved_out
    }

    override fun onInitView(binding: ViewDataBinding, view: View) {
        mBinding = binding as FragmentMovedOutBinding
        mBinding.apply {
            toolbarMovedOut.apply {
                ivToolBarBack.setOnClickListener {
                    onBackPress()
                }
                tvToolBarTitle.text = getString(R.string.move_out)
                ivToolBarRightIcon.visibility = View.VISIBLE
            }

            moveOutBtn.setOnClickListener {
                CustomDialogs.showCustomSuccessDialog(requireContext(),
                    getString(R.string.payment_you_have_successfully_text),
                    getString(R.string.payment_our_representative_text),
                    getString(R.string.title_okay),
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