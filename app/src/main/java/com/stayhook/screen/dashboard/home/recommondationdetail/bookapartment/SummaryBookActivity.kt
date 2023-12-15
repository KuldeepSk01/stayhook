package com.stayhook.screen.dashboard.home.recommondationdetail.bookapartment

import android.app.Dialog
import android.util.Log
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.stayhook.R
import com.stayhook.base.BaseActivity
import com.stayhook.databinding.ActivitySummaryBookBinding
import com.stayhook.model.request.PropertyRoomRequest
import com.stayhook.model.response.SuccessErrorResponse
import com.stayhook.model.response.getpopertydetail.PropertyRoom
import com.stayhook.network.ApiResponse
import com.stayhook.screen.dashboard.account.mybooking.MyBookingActivity
import com.stayhook.screen.dashboard.home.recommondationdetail.PaymentViewModel
import com.stayhook.util.Constants
import com.stayhook.util.CustomDialogs
import com.stayhook.util.Utility
import com.stayhook.util.serializable
import org.koin.core.component.inject

class SummaryBookActivity : BaseActivity() {

    private val paymentVM: PaymentViewModel by inject()
    private lateinit var sbBinding: ActivitySummaryBookBinding
    private val paymentActivity: PaymentActivity by lazy {
        PaymentActivity()
    }
    override val layoutId: Int
        get() = R.layout.activity_summary_book

    override fun onViewInit(binding: ViewDataBinding?) {
        sbBinding = binding as ActivitySummaryBookBinding
        val propertyRoom =
            intent.getBundleExtra("bundleToken")?.serializable<PropertyRoom>("tokenRequest")!!
        Log.d("TAG", "onViewInit: Summary $propertyRoom")

        val token = propertyRoom.price / 2
        sbBinding.apply {
            toolbarSumary.apply {
                ivToolBarBack.setOnClickListener {
                    onBackPressedDispatcher.onBackPressed()
                }
                tvToolBarTitle.text = getString(R.string.summary)
            }
            tvMonthlyRentSummary.text = String.format(
                "%s %d",
                getString(R.string.indian_currency_symbol),
                propertyRoom.price
            )
            tvTokenPercentageSummary.text = String.format("%s", "50%")
            tvTotalTokenAmountSummary.text =
                String.format("%s %d", getString(R.string.indian_currency_symbol), token)
            btnPaySummary.apply {
                text = String.format(
                    "%s %s%d",
                    "Token",
                    getString(R.string.indian_currency_symbol),
                    token
                )
                setOnClickListener {
                    if (!Utility.isConnectionAvailable()){
                        CustomDialogs.showErrorMessage(
                            this@SummaryBookActivity,
                            Constants.NetworkConstant.NO_INTERNET_AVAILABLE
                        )
                        return@setOnClickListener
                    }

                    val request = PropertyRoomRequest().apply {
                        propertyRoom.let {
                            if (it.roomId==null){
                                propertyId = it.propertyId
                                roomId = it.id.toString()
                                roomRent = it.price.toString()
                                tokenAmount = token.toString()
                                availabilityDate = it.availabilityDate
                                roomType = it.roomTypeId.toString()

                            }else{
                                bedId = it.id.toString()
                                propertyId = it.propertyId
                                roomId = it.roomId
                                roomRent = it.price.toString()
                                tokenAmount = token.toString()
                                availabilityDate = it.availabilityDate
                                roomType = it.roomTypeId.toString()
                            }

                        }

                    }

                    Log.d("TAG", "onViewInit: request $request")
                    paymentVM.hitTokenCollected(request)
                    paymentVM.getTokenCollectedResponse()
                        .observe(this@SummaryBookActivity, paymentTokenResponseObserver)

                    /*  val b = Bundle()
                      b.putSerializable("tokenRequest", propertyRoom)
                      launchActivity(PaymentActivity::class.java, "bundleToken", b)*/
                }
            }

        }
    }

    private val paymentTokenResponseObserver: Observer<ApiResponse<SuccessErrorResponse>> by lazy {
        Observer {
            when (it.status) {
                ApiResponse.Status.LOADING -> {
                    showProgress()
                }

                ApiResponse.Status.SUCCESS -> {
                    hideProgress()

                    CustomDialogs.showCustomSuccessDialog(
                        this@SummaryBookActivity,
                        getString(R.string.booking_complete_text),
                        getString(R.string.our_representative_will_contact_you_shortly_text),
                        getString(R.string.my_booking),
                        object : CustomDialogs.CustomDialogsListener {
                            override fun onComplete(d: Dialog) {
                                launchActivity(MyBookingActivity::class.java)
                                finish()
                                d.dismiss()

                            }

                        }).show()
                }

                ApiResponse.Status.ERROR -> {
                    hideProgress()
                    CustomDialogs.showErrorMessage(
                        this@SummaryBookActivity,
                        it.error?.message.toString()
                    )
                }
            }
        }
    }


}