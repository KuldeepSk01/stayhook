package com.stayhook.screen.dashboard.account.mypayment

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.stayhook.R
import com.stayhook.adapter.MyPaymentAdapter
import com.stayhook.adapter.interfaces.OnPaymentListener
import com.stayhook.base.BaseActivity
import com.stayhook.base.CollectionBaseResponse
import com.stayhook.databinding.ActivityMyPaymentsBinding
import com.stayhook.model.response.MyPaymentsResponse
import com.stayhook.network.ApiResponse
import org.koin.core.component.inject

class MyPaymentsActivity : BaseActivity(), OnPaymentListener {

    private lateinit var mPBinding: ActivityMyPaymentsBinding
    private val mViewModel: MyPaymentViewModel by inject()

    override val layoutId: Int
        get() = R.layout.activity_my_payments

    override fun onViewInit(binding: ViewDataBinding?) {
        mPBinding = binding as ActivityMyPaymentsBinding
        setBackGround(mPBinding.tvPendingPaymentMP)


        mPBinding.apply {
            toolbarMyPayment.apply {
                ivToolBarBack.setOnClickListener {
                    onBackPressedDispatcher.onBackPressed()
                }
                tvToolBarTitle.text = getString(R.string.my_payment_text)
            }

            tvPendingPaymentMP.setOnClickListener {
                setBackGround(it as AppCompatTextView)
            }
            tvLastPaymentMP.setOnClickListener {
                setBackGround(it as AppCompatTextView)
            }

        }
    }

    private fun setBackGround(
        tv1: AppCompatTextView,
    ) {
        mPBinding.apply {
            tvPendingPaymentMP.background = null
            tvLastPaymentMP.background = null
        }
        tv1.background =
            ResourcesCompat.getDrawable(resources, R.drawable.select_white_bg, null)
        mPBinding.rlNoDataFound.visibility = View.GONE
        mViewModel.hitMyPayment(tv1.text.toString(), "")
        mViewModel.paymentsResponse.observe(this@MyPaymentsActivity, mObserver)


    }

    private val mObserver: Observer<ApiResponse<CollectionBaseResponse<MyPaymentsResponse>>> by lazy {
        Observer {
            when (it.status) {

                ApiResponse.Status.LOADING -> {
                    showProgress()
                }

                ApiResponse.Status.SUCCESS -> {
                    hideProgress()

                    if (it.data?.data?.isEmpty()!!) {
                        mPBinding.rvMyPayment.visibility = View.GONE
                        mPBinding.rlNoDataFound.visibility = View.VISIBLE
                        mPBinding.animationView.playAnimation()
                    } else {
                        mPBinding.rvMyPayment.visibility = View.VISIBLE
                        mPBinding.rlNoDataFound.visibility = View.GONE
                        setPaymentList(it.data.data as MutableList<MyPaymentsResponse>)
                        Log.d("TAG", "MyPayment: ${it.data.data}")
                    }


                }

                ApiResponse.Status.ERROR -> {
                    hideProgress()
                }

            }
        }
    }

    private fun setPaymentList(data: MutableList<MyPaymentsResponse>?) {

        mPBinding.apply {
            rvMyPayment.apply {
                itemAnimator = DefaultItemAnimator()
                layoutManager =
                    LinearLayoutManager(
                        this@MyPaymentsActivity,
                        LinearLayoutManager.VERTICAL,
                        false
                    )

                adapter = MyPaymentAdapter(data!!, this@MyPaymentsActivity, this@MyPaymentsActivity)


            }
        }
    }

    override fun onPayClickListener(model: MyPaymentsResponse) {
        Toast.makeText(this@MyPaymentsActivity, "working...", Toast.LENGTH_SHORT).show()
    }


}