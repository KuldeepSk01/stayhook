package com.stayhook.screen.dashboard.account.myschedule

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.stayhook.R
import com.stayhook.adapter.MyBookingAdapter
import com.stayhook.base.BaseActivity
import com.stayhook.base.CollectionBaseResponse
import com.stayhook.databinding.ActivityMyScheduledBinding
import com.stayhook.model.response.TokenCollectedResponse
import com.stayhook.network.ApiResponse
import com.stayhook.util.CustomDialogs
import org.koin.core.component.inject

class MyScheduledVisitActivity : BaseActivity(), MyBookingAdapter.OnClickTokenListener {
    private lateinit var mBinding: ActivityMyScheduledBinding
    private val mViewModel: MyScheduleVisitViewModel by inject()

    override val layoutId: Int
        get() = R.layout.activity_my_scheduled

    override fun onViewInit(binding: ViewDataBinding?) {
        mBinding = binding as ActivityMyScheduledBinding

        mBinding.apply {
            setBackGround(tvCurrentMB)
            mViewModel.hitGetScheduleToken()
            mViewModel.getScheduledTokenResponse()
                .observe(this@MyScheduledVisitActivity, getScheduledTokenResponseObserver)

            toolBarMyScheduledVisit.apply {
                ivToolBarBack.visibility = View.INVISIBLE
                tvToolBarTitle.text = getString(R.string.my_scheduled_visit)

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
                    setTokenList(it.data?.data!! as MutableList<TokenCollectedResponse>)
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
            adapter = MyBookingAdapter(data!!, this@MyScheduledVisitActivity,this@MyScheduledVisitActivity)
        }
    }

    private fun setBackGround(

        tv1: AppCompatTextView,
    ) {
        mBinding.apply {
            tvCurrentMB.background =
                ResourcesCompat.getDrawable(resources, R.drawable.otp_box_background, null)
            tvPastMB.background =
                ResourcesCompat.getDrawable(resources, R.drawable.otp_box_background, null)
            tvUpcomingMB.background =
                ResourcesCompat.getDrawable(resources, R.drawable.otp_box_background, null)

        }
        tv1.background =
            ResourcesCompat.getDrawable(resources, R.drawable.otp_box_outline_drawable, null)
    }

    override fun onClickToken(model: TokenCollectedResponse) {
        launchActivity(ScheduleVisitStatusActivity::class.java,"tokenId",model.id.toString())
    }

}