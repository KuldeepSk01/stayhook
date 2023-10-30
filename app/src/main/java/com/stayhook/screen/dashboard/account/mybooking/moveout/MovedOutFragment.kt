package com.stayhook.screen.dashboard.account.mybooking.moveout

import android.app.Dialog
import android.view.View
import android.widget.CalendarView
import androidx.databinding.ViewDataBinding
import com.stayhook.R
import com.stayhook.base.BaseFragment
import com.stayhook.databinding.FragmentMovedOutBinding
import com.stayhook.util.CustomDialogs
import com.stayhook.util.getDateFormat
import com.stayhook.util.getTimeFormat

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

            calenderView.setOnDateChangeListener(object:CalendarView.OnDateChangeListener{
                override fun onSelectedDayChange(p0: CalendarView, p1: Int, p2: Int, p3: Int) {
                    val year = p1
                    val month = p2 + 1
                    val dayOfMonth = p3
                    moveInTV.text = getDateFormat(dayOfMonth, month, year)                }

            })
            moveOutBtn.setOnClickListener {
                CustomDialogs.showCustomSuccessDialog(requireContext(),
                    getString(R.string.payment_you_have_successfully_text),
                    getString(R.string.payment_our_representative_text),
                    getString(R.string.title_okay),
                    object : CustomDialogs.CustomDialogsListener {
                        override fun onComplete(d: Dialog) {
                            removeAllFragments()
                            d.dismiss()
                        }
                    }).show()
            }

        }
    }

    private fun removeAllFragments() {
        val fm = requireActivity().supportFragmentManager
        for (i in 0 until fm.backStackEntryCount) {
            fm.popBackStack()
        }
        // onBackPress()
    }

}