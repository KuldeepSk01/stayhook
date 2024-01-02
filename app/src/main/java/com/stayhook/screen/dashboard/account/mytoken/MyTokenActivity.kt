package com.stayhook.screen.dashboard.account.mytoken

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
import com.stayhook.databinding.ActivityMyTokenBinding
import com.stayhook.model.response.TokenCollectedResponse
import com.stayhook.network.ApiResponse
import com.stayhook.screen.dashboard.account.myschedule.ScheduleVisitStatusActivity
import com.stayhook.util.CustomDialogs
import com.stayhook.util.mLog
import org.koin.core.component.inject

class MyTokenActivity : BaseActivity(), MyScheduleVisitAdapter.OnClickTokenListener {
    private lateinit var mBind: ActivityMyTokenBinding
    private val mViewModel: MyTokenViewModel by inject()

    override val layoutId: Int
        get() = R.layout.activity_my_token

    override fun onViewInit(binding: ViewDataBinding?) {
        mBind = binding as ActivityMyTokenBinding
        mBind.apply {
            //setBackGround(tvCurrentMB)
            mViewModel.hitGetTokenCollected()
            mViewModel.getTokenCollectedResponse()
                .observe(this@MyTokenActivity, getTokenResponseObserver)
            toolBarMyToken.apply {
                tvToolBarTitle.text = getString(R.string.my_token)
                ivToolBarBack.setOnClickListener {
                    onBackPressedDispatcher.onBackPressed()
                }

            }

/*

            tvCurrentMB.setOnClickListener {
                setBackGround(it as AppCompatTextView)
            }
            tvPastMB.setOnClickListener {
                setBackGround(it as AppCompatTextView)
            }
            tvUpcomingMB.setOnClickListener {
                setBackGround(it as AppCompatTextView)
            }
*/
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
                    if (it.data?.data?.isEmpty()!!) {
                        mBind.rvMyToken.visibility = View.GONE
                        mBind.rlNoDataFound.visibility = View.VISIBLE
                        mBind.animationView.playAnimation()
                    } else {
                        mBind.rvMyToken.visibility = View.VISIBLE
                        mBind.rlNoDataFound.visibility = View.GONE
                        setTokenList(it.data?.data!! as MutableList<TokenCollectedResponse>)
                        mLog("MyScheduleVisit: ${it.data.data}")
                    }

                }

                ApiResponse.Status.ERROR -> {
                    hideProgress()
                    CustomDialogs.showErrorMessage(
                        this@MyTokenActivity, it.error?.message.toString()
                    )
                }
            }
        }
    }


    private fun setBackGround(

        tv1: AppCompatTextView,
    ) {
      /*  mBind.apply {
            tvCurrentMB.background = null
            tvPastMB.background = null
            tvUpcomingMB.background = null
        }*/
        tv1.background = ResourcesCompat.getDrawable(resources, R.drawable.select_white_bg, null)
    }

    private fun setTokenList(data: MutableList<TokenCollectedResponse>?) {
        mBind.rvMyToken.apply {
            layoutManager = LinearLayoutManager(this@MyTokenActivity)
            adapter = MyScheduleVisitAdapter(data!!, this@MyTokenActivity, this@MyTokenActivity)
        }

    }

    override fun onClickToken(model: TokenCollectedResponse) {
        launchActivity(ScheduleVisitStatusActivity::class.java, "tokenId", model.id.toString())
    }


}