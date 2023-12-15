package com.stayhook.screen.dashboard.account.myschedule

import android.util.Log
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.stayhook.R
import com.stayhook.base.BaseActivity
import com.stayhook.base.BaseResponse
import com.stayhook.databinding.ActivityScheduleVisitStatusBinding
import com.stayhook.model.response.TokenCollectedResponse
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

    private val mObserver: Observer<ApiResponse<BaseResponse<TokenCollectedResponse>>> by lazy {
        Observer { it ->
            when (it.status) {
                ApiResponse.Status.LOADING -> {
                    showProgress()
                }

                ApiResponse.Status.SUCCESS -> {
                    hideProgress()
                    it.data?.data?.history?.forEach {
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
                    "Agreement"->{
                        clAgreementLayout.visibility = View.VISIBLE
                        tvAgreementStatus.text = data.status
                        tvRemarkAgreement.text = data.remark
                        tvCreatedTimeAgreement.text = data.created_at
                        Glide.with(this@ScheduleVisitStatusActivity).load(data.agreement).into(ivAgreement)
                        Glide.with(this@ScheduleVisitStatusActivity).load(data.police_verification).into(ivPoliceVerification)
                        Glide.with(this@ScheduleVisitStatusActivity).load(data.signatured_agreement).into(ivSignAgreement)

                    }
                    "Visits"->{
                        tvVisitName.text = data.fieldPerson
                        clVisitLayout.visibility=View.VISIBLE
                        tvVisitDate.text= data.created_at
                        tvVisitTime.text = data.visitTime
                        tvVisitStatus.text = data.status
                        tvRemarkVisit.text = data.remark

                    }
                    else->{
                        clTokenCollectedLayout.visibility=View.VISIBLE
                        tvTokenCollectedStatus.text = data.status
                        tvCreatedTimeTC.text = data.created_at
                        tvRemarkTC.text = data.remark
                    }
                }

            }
        }

    }
}