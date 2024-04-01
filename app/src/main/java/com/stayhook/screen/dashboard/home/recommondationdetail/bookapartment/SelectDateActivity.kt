package com.stayhook.screen.dashboard.home.recommondationdetail.bookapartment

import android.os.Bundle
import android.util.Log
import android.widget.CalendarView
import android.widget.CalendarView.OnDateChangeListener
import androidx.databinding.ViewDataBinding
import com.stayhook.R
import com.stayhook.base.BaseActivity
import com.stayhook.databinding.ActivitySelectDateBinding
import com.stayhook.model.response.getpopertydetail.PropertyRoom
import com.stayhook.util.Constants
import com.stayhook.util.CustomDialogs
import com.stayhook.util.Utility
import com.stayhook.util.formatDate
import com.stayhook.util.getCurrentDate
import com.stayhook.util.selectDateFormat
import com.stayhook.util.serializable

class SelectDateActivity : BaseActivity() {

    private lateinit var sdBinding: ActivitySelectDateBinding

    override val layoutId: Int
        get() = R.layout.activity_select_date

    override fun onViewInit(binding: ViewDataBinding?) {
        sdBinding = binding as ActivitySelectDateBinding
        val propertyRoom =
            intent.getBundleExtra("bundleToken")?.serializable<PropertyRoom>("tokenRequest")!!
        Log.d("TAG", "onViewInit: $propertyRoom")
        sdBinding.apply {
            moveInTV.text = getCurrentDate()
            toolBarSelectDate.apply {
                ivToolBarBack.setOnClickListener {
                    onBackPressedDispatcher.onBackPressed()
                }
                tvToolBarTitle.text = getString(R.string.move_in_date)
            }

            calenderViewSD.minDate = System.currentTimeMillis() - 1000
            calenderViewSD.setOnDateChangeListener(object : OnDateChangeListener {
                override fun onSelectedDayChange(p0: CalendarView, p1: Int, p2: Int, p3: Int) {
                    val year = p1
                    val month = p2
                    val dayOfMonth = p3
                    moveInTV.text = selectDateFormat(dayOfMonth, month, year)
                }

            })


            btnSelectMoveInDate.setOnClickListener {
                if (!Utility.isNetworkAvailable(this@SelectDateActivity)) {
                    CustomDialogs.showErrorMessage(
                        this@SelectDateActivity,
                        Constants.NetworkConstant.NO_INTERNET_AVAILABLE
                    )
                    return@setOnClickListener
                }

                propertyRoom.availabilityDate =
                    formatDate(moveInTV.text.toString(), "dd MMM yyyy", "yyyy-MM-dd")
                //   getSelectedDateForApi()
                //propertyRoom.availabilityDate = moveInDate
                val b = Bundle()
                b.putSerializable("tokenRequest", propertyRoom)
                launchActivity(SummaryBookActivity::class.java, "bundleToken", b)
            }

        }
    }


}