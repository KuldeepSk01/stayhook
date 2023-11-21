package com.stayhook.screen.dashboard.account.myschedule

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.stayhook.R
import com.stayhook.base.BaseActivity
import com.stayhook.databinding.ActivityScheduleVisitStatusBinding
import com.stayhook.model.response.scheduletokendetail.History
import com.stayhook.model.response.scheduletokendetail.ScheduleTokenDetailResponse
import com.stayhook.network.ApiResponse
import com.stayhook.util.CustomDialogs
import org.koin.core.component.inject

class ScheduleVisitStatusActivity : BaseActivity() {
    private lateinit var sBinding: ActivityScheduleVisitStatusBinding
    private val mViewModel: MyScheduleVisitStatusViewModel by inject()
    override val layoutId: Int
        get() = R.layout.activity_schedule_visit_status

    override fun onViewInit(binding: ViewDataBinding?) {

        sBinding = binding as ActivityScheduleVisitStatusBinding
        val tokenId = intent.getStringExtra("tokenId")
        sBinding.apply {
            toolBarMyScheduledVisitStatus.apply {
                ivToolBarBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
                tvToolBarTitle.text = getString(R.string.token_status)
            }
            mViewModel.hitGetScheduledTokenDetail(tokenId!!)
            mViewModel.getScheduledTokenDetailResponse()
                .observe(this@ScheduleVisitStatusActivity, mObserver)
        }
    }

    private val mObserver: Observer<ApiResponse<ScheduleTokenDetailResponse>> by lazy {
        Observer { it ->
            when (it.status) {
                ApiResponse.Status.LOADING -> {
                    showProgress()
                }

                ApiResponse.Status.SUCCESS -> {
                    hideProgress()
                    it.data?.history?.forEach {
                        setTrackStatus(it)
                    }
                }

                ApiResponse.Status.ERROR -> {
                    hideProgress()
                    CustomDialogs.showErrorMessage(
                        this@ScheduleVisitStatusActivity,
                        it.error?.message.toString()
                    )
                }
            }
        }
    }

    private fun setTrackStatus(data: History) {
        sBinding.apply {
            data.let {
                when(it.status){
                    "Agreement"->{}
                    "Visits"->{}
                }

            }
        }

    }
}