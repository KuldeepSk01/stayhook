package com.stayhook.screen.dashboard.account.mybooking

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
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
import com.stayhook.model.response.MyBookingResponse
import com.stayhook.network.ApiResponse
import com.stayhook.screen.dashboard.MainActivity
import com.stayhook.screen.dashboard.account.mybooking.moveout.MovedOutActivity
import com.stayhook.util.Constants
import com.stayhook.util.CustomDialogs
import com.stayhook.util.mLog
import org.koin.core.component.inject

class MyBookingActivity : BaseActivity(), MyBookingAdapter.OnMyBookingListener {
    private lateinit var mBinding: FragmentMyBookingBinding
    private val mViewModel: MyBookingViewModel by inject()

    override val layoutId: Int
        get() = R.layout.fragment_my_booking

    override fun onViewInit(binding: ViewDataBinding?) {
        mBinding = binding as FragmentMyBookingBinding

        mBinding.apply {
            // setBackGround(tvCurrentMB)
            mViewModel.hitGetMyBookingApi()
            mViewModel.getMyBookingResponse()
                .observe(this@MyBookingActivity, getMyBookingResponseObserver)

            toolBarMyBooking.apply {
                tvToolBarTitle.text = getString(R.string.my_booking)
                ivToolBarBack.setOnClickListener {
                    //onBackPressedDispatcher.onBackPressed()
                    if (mPref.get(Constants.PreferenceConstant.IS_BACK_PRESS_TRUE,0)==1){
                        onBackPressedDispatcher.onBackPressed()
                    }else{
                        launchActivity(MainActivity::class.java)
                        finish()
                    }
                }

            }
            /*onBackPressedDispatcher.addCallback(this@MyBookingActivity, object : OnBackPressedCallback(true){
                override fun handleOnBackPressed() {
                    onBackPressedDispatcher.onBackPressed()
                    *//*if (mPref.get(Constants.PreferenceConstant.IS_BACK_PRESS_TRUE,0)==1){
                        onBackPressedDispatcher.onBackPressed()
                    }else{
                        launchActivity(MainActivity::class.java)
                        finish()
                    }*//*
                }

            })*/

            /*  tvCurrentMB.setOnClickListener {
                  setBackGround(it as AppCompatTextView)
              }
              tvPastMB.setOnClickListener {
                  setBackGround(it as AppCompatTextView)
              }
              tvUpcomingMB.setOnClickListener {
                  setBackGround(it as AppCompatTextView)
              }*/
        }

    }

    private val getMyBookingResponseObserver: Observer<ApiResponse<CollectionBaseResponse<MyBookingResponse>>> by lazy {
        Observer {
            when (it.status) {
                ApiResponse.Status.LOADING -> {
                    showProgress()
                }

                ApiResponse.Status.SUCCESS -> {
                    hideProgress()
                    if (it.data?.data?.isEmpty()!!) {
                        mBinding.rvMyBooking.visibility = View.GONE
                        mBinding.rlNoDataFound.visibility = View.VISIBLE
                        mBinding.animationView.playAnimation()
                    } else {
                        mBinding.rvMyBooking.visibility = View.VISIBLE
                        mBinding.rlNoDataFound.visibility = View.GONE
                        setMyBookingList(it.data.data as MutableList<MyBookingResponse>)
                        mLog("MyBookingList: ${it.data.data}")
                    }
                }

                ApiResponse.Status.ERROR -> {
                    hideProgress()
                    CustomDialogs.showErrorMessage(
                        this@MyBookingActivity, it.error?.message.toString()
                    )
                }
            }
        }
    }

    private fun setMyBookingList(data: MutableList<MyBookingResponse>?) {
        mBinding.rvMyBooking.apply {
            layoutManager = LinearLayoutManager(this@MyBookingActivity)
            adapter = MyBookingAdapter(data!!, this@MyBookingActivity, this@MyBookingActivity)
        }

    }

    private fun setBackGround(

        tv1: AppCompatTextView,
    ) {/*mBinding.apply {
            tvCurrentMB.background = null
            tvPastMB.background = null
            tvUpcomingMB.background = null
        }*/
        tv1.background = ResourcesCompat.getDrawable(resources, R.drawable.select_white_bg, null)
    }


    override fun onBackPressed() {
        super.onBackPressed()
        if (mPref.get(Constants.PreferenceConstant.IS_BACK_PRESS_TRUE,0)==1){
            onBackPressedDispatcher.onBackPressed()
        }else{
            launchActivity(MainActivity::class.java)
            finish()
        }
    }

    override fun onMoveOutBooking(model: MyBookingResponse) {
        val b = Bundle()
        b.putSerializable(Constants.DefaultConstants.MODEL_DETAIL, model)
        launchActivity(MovedOutActivity::class.java, Constants.DefaultConstants.BUNDLE, b)
    }


}