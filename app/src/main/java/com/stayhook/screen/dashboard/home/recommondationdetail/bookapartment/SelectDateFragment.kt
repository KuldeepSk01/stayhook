package com.stayhook.screen.dashboard.home.recommondationdetail.bookapartment

import android.util.Log
import android.view.View
import android.widget.CalendarView
import android.widget.CalendarView.OnDateChangeListener
import androidx.databinding.ViewDataBinding
import com.stayhook.R
import com.stayhook.base.BaseFragment
import com.stayhook.databinding.FragmentSelectDateBinding
import com.stayhook.util.getDateFormat

class SelectDateFragment : BaseFragment() {

    private lateinit var sdBinding: FragmentSelectDateBinding
    private val summaryFragment: SummaryBookFragment by lazy {
        SummaryBookFragment()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_select_date
    }

    override fun onInitView(binding: ViewDataBinding, view: View) {
        sdBinding = binding as FragmentSelectDateBinding
        sdBinding.apply {
            toolBarSelectDate.apply {
                ivToolBarBack.setOnClickListener {
                    onBackPress()
                }
                tvToolBarTitle.text = getString(R.string.move_in_date)
            }
            btnSelectMoveInDate.setOnClickListener {
                replaceFragment(
                    R.id.flMainContainer,
                    summaryFragment,
                    this@SelectDateFragment.javaClass.simpleName
                )
                Log.d(
                    "RDFragment",
                    "onInitView RDFragment: className ${this@SelectDateFragment.javaClass.simpleName}"
                )
            }


            calenderViewSD.setOnDateChangeListener(object : OnDateChangeListener {
                override fun onSelectedDayChange(p0: CalendarView, p1: Int, p2: Int, p3: Int) {
                    val year = p1
                    val month = p2 + 1
                    val dayOfMonth = p3
                    moveInTV.text = getDateFormat(dayOfMonth, month, year)
                }

            })


        }

    }

}