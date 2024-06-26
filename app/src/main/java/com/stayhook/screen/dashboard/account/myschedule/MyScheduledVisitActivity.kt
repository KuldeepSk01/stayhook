package com.stayhook.screen.dashboard.account.myschedule

import android.util.Log
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.stayhook.R
import com.stayhook.adapter.MyScheduleVisitAdapter
import com.stayhook.base.BaseActivity
import com.stayhook.base.CollectionBaseResponse
import com.stayhook.databinding.ActivityMyScheduledBinding
import com.stayhook.model.response.MyBookingResponse
import com.stayhook.model.response.TokenCollectedResponse
import com.stayhook.network.ApiResponse
import com.stayhook.util.CustomDialogs
import com.stayhook.util.mLog
import org.koin.core.component.inject

 class MyScheduledVisitActivity : BaseActivity(), MyScheduleVisitAdapter.OnClickTokenListener {
    private lateinit var mBinding: ActivityMyScheduledBinding
    private val mViewModel: MyScheduleVisitViewModel by inject()

    override val layoutId: Int
        get() = R.layout.activity_my_scheduled

    override fun onViewInit(binding: ViewDataBinding?) {
        mBinding = binding as ActivityMyScheduledBinding

        mBinding.apply {
            setBackGround(tvCurrentMB)

            toolBarMyScheduledVisit.apply {
                tvToolBarTitle.text = getString(R.string.my_scheduled_visit)
                ivToolBarBack.setOnClickListener {
                    onBackPressedDispatcher.onBackPressed()
                }

            }


            tvCurrentMB.setOnClickListener {
                setBackGround(it as AppCompatTextView)
            }
            tvPastMB.setOnClickListener {
                setBackGround(it as AppCompatTextView)
            }
            tvUpcomingMB.setOnClickListener {
                setBackGround(it as AppCompatTextView)
            }

        }

    }

    private val getScheduledTokenResponseObserver: Observer<ApiResponse<CollectionBaseResponse<TokenCollectedResponse>>> by lazy {
        Observer {
            when (it.status) {
                ApiResponse.Status.LOADING -> {
                    showProgress()
                }

                ApiResponse.Status.SUCCESS -> {
                    hideProgress()
                    if (it.data?.data?.isEmpty()!!) {
                        mBinding.rvMyScheduledVisit.visibility = View.GONE
                        mBinding.rlNoDataFound.visibility = View.VISIBLE
                        mBinding.animationView.playAnimation()
                    } else {
                        mBinding.rvMyScheduledVisit.visibility = View.VISIBLE
                        mBinding.rlNoDataFound.visibility = View.GONE
                        setTokenList(it.data?.data!! as MutableList<TokenCollectedResponse>)
                        mLog("MyScheduleVisit: ${it.data.data}")
                    }

                }

                ApiResponse.Status.ERROR -> {
                    hideProgress()
                    CustomDialogs.showErrorMessage(
                        this@MyScheduledVisitActivity,
                        it.error?.message.toString()
                    )
                }
            }
        }
    }

    private fun setTokenList(data: MutableList<TokenCollectedResponse>?) {
        mBinding.rvMyScheduledVisit.apply {
            layoutManager = LinearLayoutManager(this@MyScheduledVisitActivity)
            adapter = MyScheduleVisitAdapter(data!!, this@MyScheduledVisitActivity,this@MyScheduledVisitActivity)
        }
    }

    private fun setBackGround(

        tv1: AppCompatTextView,
    ) {
        mBinding.apply {
            tvCurrentMB.background = null
            tvPastMB.background = null
            tvUpcomingMB.background = null
        }
        tv1.background =
            ResourcesCompat.getDrawable(resources, R.drawable.select_white_bg, null)


        mViewModel.hitGetScheduleToken(tv1.text.toString())
        mViewModel.getScheduledTokenResponse()
            .observe(this@MyScheduledVisitActivity, getScheduledTokenResponseObserver)


    }

    override fun onClickToken(model: TokenCollectedResponse) {
        launchActivity(ScheduleVisitStatusActivity::class.java,"tokenId",model.id.toString())
    }

}