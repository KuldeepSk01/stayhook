package com.stayhook.screen.dashboard.account.mybooking

import android.os.Bundle
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
import com.stayhook.databinding.FragmentMyBookingBinding
import com.stayhook.model.response.TokenCollectedResponse
import com.stayhook.network.ApiResponse
import com.stayhook.screen.dashboard.MainActivity
import com.stayhook.screen.dashboard.account.mybooking.moveout.MovedOutActivity
import com.stayhook.screen.dashboard.account.myschedule.ScheduleVisitStatusActivity
import com.stayhook.util.Constants
import com.stayhook.util.CustomDialogs
import org.koin.core.component.inject

class MyBookingActivity : BaseActivity(), MyBookingAdapter.OnClickTokenListener {
    private lateinit var mBinding: FragmentMyBookingBinding
    private val mViewModel: MyBookingViewModel by inject()

    override val layoutId: Int
        get() = R.layout.fragment_my_booking

    override fun onViewInit(binding: ViewDataBinding?) {
        mBinding = binding as FragmentMyBookingBinding

        mBinding.apply {
            setBackGround(tvCurrentMB)
            mViewModel.hitGetTokenCollected()
            mViewModel.getTokenCollectedResponse()
                .observe(this@MyBookingActivity, getTokenResponseObserver)

            toolBarMyBooking.apply {
                ivToolBarBack.visibility = View.INVISIBLE
                tvToolBarTitle.text = getString(R.string.my_booking)

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

            /*moveOutBT.setOnClickListener {
                replaceFragment(R.id.flMainContainer, MovedOutFragment(), MyBookingFragment().javaClass.simpleName)
            }*/
        }

    }

    private val getTokenResponseObserver: Observer<ApiResponse<CollectionBaseResponse<TokenCollectedResponse>>> by lazy {
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
                        this@MyBookingActivity,
                        it.error?.message.toString()
                    )
                }
            }
        }
    }

    private fun setTokenList(data: MutableList<TokenCollectedResponse>?) {
        mBinding.rvMyBooking.apply {
            layoutManager = LinearLayoutManager(this@MyBookingActivity)
            adapter = MyBookingAdapter(data!!, this@MyBookingActivity,this@MyBookingActivity)
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
    }

    override fun onClickToken(model: TokenCollectedResponse) {
        launchActivity(ScheduleVisitStatusActivity::class.java,"tokenId",model.id.toString())
    }

    override fun onMovingOut(model: TokenCollectedResponse) {
        val b = Bundle()
        b.putSerializable(Constants.DefaultConstants.MODEL_DETAIL,model)
        launchActivity(MovedOutActivity::class.java,Constants.DefaultConstants.BUNDLE,b)

    }


    override fun onBackPressed() {
        super.onBackPressed()
        launchActivity(MainActivity::class.java)
        finish()
    }


}