package com.stayhook.screen.dashboard.account.myschedule

import android.util.Log
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.stayhook.R
import com.stayhook.adapter.TokenStatusAdapter
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
                    setStatusList(it.data?.data?.history as MutableList<History>)
//                    it.data?.data?.history?.forEach {
//                        //setTrackStatus(it)
//                    }



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

    private fun setStatusList(history: MutableList<History>?) {
        sBinding.rvtokenStatus.apply {
            layoutManager = LinearLayoutManager(this@ScheduleVisitStatusActivity,LinearLayoutManager.VERTICAL,true)
            adapter = TokenStatusAdapter(history!!,this@ScheduleVisitStatusActivity)
        }
    }

    private fun setTrackStatus(data: History) {
        /*     sBinding.apply {
            data.let {
                when(it.status){
                    "Agreement"->{
                        clAgreementLayout.visibility = View.VISIBLE
                        //clVisitLayout.visibility=View.VISIBLE
                        //clTokenCollectedLayout.visibility=View.VISIBLE

                        tvAgreementStatus.text = data.status
                        tvRemarkAgreement.text = data.remark
                        tvCreatedTimeAgreement.text = data.created_at
                        Glide.with(this@ScheduleVisitStatusActivity).load(data.agreement).placeholder(R.drawable.default_image).into(ivAgreement)
                        Glide.with(this@ScheduleVisitStatusActivity).load(data.police_verification).placeholder(R.drawable.default_image).into(ivPoliceVerification)
                        Glide.with(this@ScheduleVisitStatusActivity).load(data.signatured_agreement).placeholder(R.drawable.default_image).into(ivSignAgreement)

                    }
                    "Visits"->{
                        //clAgreementLayout.visibility = View.GONE
                        clVisitLayout.visibility=View.VISIBLE
                        //clTokenCollectedLayout.visibility=View.VISIBLE

                        tvVisitStatus.text = data.status
                        tvRemarkVisitFieldName.text = data.fieldPerson
                        tvVisitDate.text= data.visitDate
                        tvVisitTime.text = data.visitTime
                        tvRemarkVisitName.text = data.remark

                    }
                    else->{

                        //clAgreementLayout.visibility = View.GONE
                        //clVisitLayout.visibility=View.GONE

                        clTokenCollectedLayout.visibility=View.VISIBLE
                        tvTokenCollectedStatus.text = data.status

                        tvCreatedTimeTC.text = data.created_at
                        tvRemarkStatusTC.text = data.remark

                    }
                }

            }
        }

    }*/
    }
}