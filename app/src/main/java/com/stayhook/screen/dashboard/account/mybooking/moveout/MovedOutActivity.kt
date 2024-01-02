package com.stayhook.screen.dashboard.account.mybooking.moveout

import android.app.Dialog
import android.view.View
import android.widget.CalendarView
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.stayhook.R
import com.stayhook.base.BaseActivity
import com.stayhook.base.CollectionBaseResponse
import com.stayhook.databinding.ActivityMovedOutBinding
import com.stayhook.model.request.MoveOutBookingRequest
import com.stayhook.model.response.MyBookingResponse
import com.stayhook.model.response.SuccessErrorResponse
import com.stayhook.network.ApiResponse
import com.stayhook.screen.dashboard.account.mybooking.MyBookingViewModel
import com.stayhook.util.Constants
import com.stayhook.util.CustomDialogs
import com.stayhook.util.getCurrentDate
import com.stayhook.util.getSelectedDateForApi
import com.stayhook.util.mLog
import com.stayhook.util.mToast
import com.stayhook.util.selectDateFormat
import com.stayhook.util.serializable
import org.koin.core.component.inject

class MovedOutActivity : BaseActivity() {
    private lateinit var mBinding: ActivityMovedOutBinding
    private val mViewModel: MyBookingViewModel by inject()

    override val layoutId = R.layout.activity_moved_out

    override fun onViewInit(binding: ViewDataBinding?) {
        mBinding = binding as ActivityMovedOutBinding
        val myBookingData =
            intent.getBundleExtra(Constants.DefaultConstants.BUNDLE)
                ?.serializable<MyBookingResponse>(Constants.DefaultConstants.MODEL_DETAIL)!!
        mLog("myBookingData $myBookingData")



        mBinding.apply {
            toolbarMovedOut.apply {
                moveInTV.text = getCurrentDate()
                ivToolBarBack.setOnClickListener {
                    onBackPressedDispatcher.onBackPressed()
                }
                tvToolBarTitle.text = getString(R.string.move_out)
                ivToolBarRightIcon.visibility = View.VISIBLE
            }

            calenderView.minDate = System.currentTimeMillis() - 1000
            calenderView.setOnDateChangeListener(object : CalendarView.OnDateChangeListener {
                override fun onSelectedDayChange(p0: CalendarView, p1: Int, p2: Int, p3: Int) {
                    val year = p1
                    val month = p2
                    val dayOfMonth = p3
                    moveInTV.text = selectDateFormat(dayOfMonth, month, year)
                }
            })


            moveOutBtn.setOnClickListener {
                val moveInDate = getSelectedDateForApi(moveInTV.text.toString())
                mLog("movie in date $moveIn")
                if (cbMoveOut.isChecked){
                    terms.visibility = View.GONE
                    val req = MoveOutBookingRequest().apply {
                        leadId = myBookingData.id
                        bookingId = myBookingData.bookingId
                        moveoutDate = moveInDate
                    }
                    mViewModel.hitMoveOutBookingApi(req)
                    mViewModel.getMoveOutBookingResponse().observe(this@MovedOutActivity, moveOutBookingResponseObserver)
                }else{
                    terms.visibility = View.VISIBLE
                }
            }

        }
    }

    private val moveOutBookingResponseObserver: Observer<ApiResponse<CollectionBaseResponse<SuccessErrorResponse>>> by lazy {
        Observer {
            when (it.status) {
                ApiResponse.Status.LOADING -> {
                    showProgress()
                }

                ApiResponse.Status.SUCCESS -> {
                    hideProgress()
                        CustomDialogs.showCustomSuccessDialog(this@MovedOutActivity,
                       getString(R.string.payment_you_have_successfully_text),
                       getString(R.string.payment_our_representative_text),
                       getString(R.string.title_okay),
                       object : CustomDialogs.CustomDialogsListener {
                           override fun onComplete(d: Dialog) {
                               d.dismiss()
                               finish()
                           }
                       }).show()

                }

                ApiResponse.Status.ERROR -> {
                    hideProgress()
                    CustomDialogs.showErrorMessage(
                        this@MovedOutActivity, it.error?.message.toString()
                    )
                }
            }
        }
    }


}