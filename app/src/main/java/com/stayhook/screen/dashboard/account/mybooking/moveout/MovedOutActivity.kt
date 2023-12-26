package com.stayhook.screen.dashboard.account.mybooking.moveout

import android.app.Dialog
import android.util.Log
import android.view.View
import android.widget.CalendarView
import androidx.databinding.ViewDataBinding
import com.stayhook.R
import com.stayhook.base.BaseActivity
import com.stayhook.databinding.ActivityMovedOutBinding
import com.stayhook.model.response.TokenCollectedResponse
import com.stayhook.model.response.getpopertydetail.PropertyRoom
import com.stayhook.model.response.home.RecommendData
import com.stayhook.util.Constants
import com.stayhook.util.CustomDialogs
import com.stayhook.util.getCurrentDate
import com.stayhook.util.getDateFormat
import com.stayhook.util.getSelectedDateForApi
import com.stayhook.util.mLog
import com.stayhook.util.selectDateFormat
import com.stayhook.util.serializable

class MovedOutActivity : BaseActivity() {
    private lateinit var mBinding: ActivityMovedOutBinding

    override val layoutId = R.layout.activity_moved_out

    override fun onViewInit(binding: ViewDataBinding?) {
        mBinding = binding as ActivityMovedOutBinding
        val tokenCollectedData =
            intent.getBundleExtra(Constants.DefaultConstants.BUNDLE)?.serializable<TokenCollectedResponse>(Constants.DefaultConstants.MODEL_DETAIL)!!
        mLog("TokenCollectedData $tokenCollectedData")
        mBinding.apply {
            toolbarMovedOut.apply {
                moveInTV.text = getCurrentDate()

                ivToolBarBack.setOnClickListener {
                    onBackPressedDispatcher.onBackPressed()
                }
                tvToolBarTitle.text = getString(R.string.move_out)
                ivToolBarRightIcon.visibility = View.VISIBLE
            }


            calenderView.minDate = System.currentTimeMillis()-1000
            calenderView.setOnDateChangeListener(object : CalendarView.OnDateChangeListener {
                override fun onSelectedDayChange(p0: CalendarView, p1: Int, p2: Int, p3: Int) {
                    val year = p1
                    val month = p2
                    val dayOfMonth = p3
                    moveInTV.text = selectDateFormat(dayOfMonth, month, year)
                }

            })
            moveOutBtn.setOnClickListener {
                binding.apply {
                    val moveIn  = getSelectedDateForApi(moveInTV.text.toString())
                    mLog("movie in date $moveIn")
                    finish()
                }

            /*    CustomDialogs.showCustomSuccessDialog(this@MovedOutActivity,
                    getString(R.string.payment_you_have_successfully_text),
                    getString(R.string.payment_our_representative_text),
                    getString(R.string.title_okay),
                    object : CustomDialogs.CustomDialogsListener {
                        override fun onComplete(d: Dialog) {
                            //removeAllFragments()

                            d.dismiss()
                        }
                    }).show()*/
            }

        }
    }


    /*  private fun removeAllFragments() {
          val fm = requireActivity().supportFragmentManager
          for (i in 0 until fm.backStackEntryCount) {
              fm.popBackStack()
          }
          // onBackPress()
      }*/


}