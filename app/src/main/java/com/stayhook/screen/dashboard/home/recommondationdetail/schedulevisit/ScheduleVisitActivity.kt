package com.stayhook.screen.dashboard.home.recommondationdetail.schedulevisit

import android.app.Dialog
import android.app.TimePickerDialog
import android.util.Log
import android.view.LayoutInflater
import android.widget.CalendarView
import android.widget.CalendarView.OnDateChangeListener
import android.widget.TimePicker
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.stayhook.R
import com.stayhook.adapter.ScheduledTimePickAdapter
import com.stayhook.base.BaseActivity
import com.stayhook.databinding.ActivityScheduleVisitBinding
import com.stayhook.databinding.LayoutScheduledTimeListBinding
import com.stayhook.model.request.ScheduleAVisitRequest
import com.stayhook.model.response.StateCityResponse
import com.stayhook.model.response.SuccessErrorResponse
import com.stayhook.model.response.getpopertydetail.GetPropertyDetail
import com.stayhook.network.ApiResponse
import com.stayhook.screen.dashboard.MainActivity
import com.stayhook.screen.dashboard.home.recommondationdetail.bookapartment.SelectDurationFragment
import com.stayhook.util.Constants
import com.stayhook.util.CustomDialogs
import com.stayhook.util.Utility
import com.stayhook.util.getDateFormat
import com.stayhook.util.getTimeFormat
import com.stayhook.util.serializable
import com.stayhook.validation.ValidationResult
import com.stayhook.validation.ValidationState
import org.koin.core.component.inject
import java.util.Calendar

class ScheduleVisitActivity : BaseActivity() {

    private lateinit var svBinding: ActivityScheduleVisitBinding
    private lateinit var propertyDetail: GetPropertyDetail
    private lateinit var amPm: String
    private val mViewModel: ScheduleAVisitViewModel by inject()


    override val layoutId: Int
        get() = R.layout.activity_schedule_visit

    override fun onViewInit(binding: ViewDataBinding?) {
        svBinding = binding as ActivityScheduleVisitBinding
        svBinding.vm = mViewModel
        propertyDetail = intent.getBundleExtra("bundleDetail")
            ?.serializable("propertyDetail")!!
        validDataObserver()
        binding.apply {
            // setBackGround(tvAMSelect)
            toolbarScheduleAVisit.apply {
                ivToolBarBack.setOnClickListener {
                    onBackPressedDispatcher.onBackPressed()
                }
                tvToolBarTitle.text = getString(R.string.schedule_visit)
            }
            /*   tvAMSelect.setOnClickListener {
                   setBackGround(tvAMSelect)
               }
               tvPMSelect.setOnClickListener {
                   setBackGround(tvPMSelect)
               }*/

            calenderView.setOnDateChangeListener { p0, p1, p2, p3 ->
                val year = p1
                val month = p2
                val day = p3
                tvAvailableDate.text = getDateFormat(day, month, year)
            }

            /*
                        tvTimePickerScheduleVisit.setOnClickListener {
                            val mcurrentTime: Calendar = Calendar.getInstance()
                            val hour: Int = mcurrentTime.get(Calendar.HOUR_OF_DAY)
                            val minute: Int = mcurrentTime.get(Calendar.MINUTE)
                            val timePickerDialog = TimePickerDialog(
                                this@ScheduleVisitActivity, 2, object : TimePickerDialog.OnTimeSetListener {
                                    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
                                        tvTimePickerScheduleVisit.text = getTimeFormat(p1, p2)
                                    }
                                }, hour, minute, true
                            )
                            timePickerDialog.show()
                        }
            */
            selectTimeCL.setOnClickListener {
                // val alertDialog = AlertDialog.Builder(this@ScheduleVisitActivity)
                val alertDialog = Dialog(this@ScheduleVisitActivity)
                val b = DataBindingUtil.inflate<LayoutScheduledTimeListBinding>(
                    LayoutInflater.from(this@ScheduleVisitActivity),
                    R.layout.layout_scheduled_time_list,
                    null,
                    false
                )
                b.rvSelectTime.apply {
                    layoutManager = LinearLayoutManager(this@ScheduleVisitActivity)
                    adapter = ScheduledTimePickAdapter(getScheduledPickTimeList(),
                        this@ScheduleVisitActivity,
                        object : ScheduledTimePickAdapter.OnScheduledTimePickerListener {
                            override fun onTimePicker(model: StateCityResponse) {
                                Log.d("TAG", "onTimePicker: time $model")
                                tvTimePickerScheduleVisit.text = model.name
                                alertDialog.dismiss()
                            }
                        })
                }
                alertDialog.setCancelable(false)
                alertDialog.setCanceledOnTouchOutside(true)
                alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                alertDialog.setContentView(b.root)
                alertDialog.create()
                alertDialog.show()

            }

            /*   binding.scheduleBtn.setOnClickListener {
                       if (!Utility.isConnectionAvailable()) {
                           CustomDialogs.showErrorMessage(
                               this@ScheduleVisitActivity, Constants.NetworkConstant.NO_INTERNET_AVAILABLE
                           )
                           return@setOnClickListener
                       }
                   }*/
        }
    }


    /*
        private fun setBackGround(tv1: AppCompatTextView) {
            svBinding.apply {
                tvAMSelect.background =
                    ResourcesCompat.getDrawable(resources, R.drawable.otp_box_background, null)
                tvPMSelect.background =
                    ResourcesCompat.getDrawable(resources, R.drawable.otp_box_background, null)
                tvAMSelect.setTextColor(resources.getColor(R.color.sub_heading_text_color, null))
                tvPMSelect.setTextColor(resources.getColor(R.color.sub_heading_text_color, null))
            }
            tv1.setTextColor(resources.getColor(R.color.primary_color, null))
            tv1.background =
                ResourcesCompat.getDrawable(resources, R.drawable.otp_box_outline_drawable, null)
            amPm = tv1.text.toString()
        }
    */

    private fun validDataObserver() {
        mViewModel.validationRegisterData.observe(this@ScheduleVisitActivity, validDataObserver)
    }

    private val validDataObserver: Observer<ValidationState> by lazy {
        Observer {
            svBinding.apply {
                when (it.msg) {
                    ValidationResult.EMPTY_FULL_NAME -> {
                        usernameET.error = getString(it.code)
                        usernameET.requestFocus()
                    }

                    ValidationResult.EMPTY_EMAIL -> {
                        userEmailET.error = getString(it.code)
                        userEmailET.requestFocus()
                    }

                    ValidationResult.EMPTY_MOBILE_NUMBER, ValidationResult.VALID_MOBILE_NUMBER -> {
                        userMobileNumberET.error = getString(it.code)
                        userMobileNumberET.requestFocus()
                    }

                    ValidationResult.SUCCESS -> {
                        if (!Utility.isConnectionAvailable()) {
                            CustomDialogs.showErrorMessage(
                                this@ScheduleVisitActivity,
                                Constants.NetworkConstant.NO_INTERNET_AVAILABLE
                            )
                            return@Observer
                        }
                        val request = ScheduleAVisitRequest().apply {
                            propertyId = propertyDetail.id.toString()
                            roomRent = propertyDetail.price.toString()
                            roomType = propertyDetail.property_type_id
                            email = userEmailET.text.toString()
                            mobileNo = userMobileNumberET.text.toString()
                            fullName = usernameET.text.toString()
                            message = userMessageET.text.toString()
                            availabilityDate = tvAvailableDate.text.toString()
                            availabilityTime = tvTimePickerScheduleVisit.text.toString() /*String.format(
                                "%s %s", tvTimePickerScheduleVisit.text.toString(), amPm
                           )*/
                        }
                        Log.d("TAG", "schedule request $request: ")

                       /*  mViewModel.hitScheduleVisit(request)
                         mViewModel.getScheduleVisitResponse()
                             .observe(this@ScheduleVisitActivity, scheduledResponseObserver)*/

                    }

                    else -> {}
                }

            }

        }
    }
    private val scheduledResponseObserver: Observer<ApiResponse<SuccessErrorResponse>> by lazy {
        Observer {
            when (it.status) {
                ApiResponse.Status.LOADING -> {
                    showProgress()
                }

                ApiResponse.Status.SUCCESS -> {
                    hideProgress()
                    launchActivity(MainActivity::class.java)
                    finish()
                }

                ApiResponse.Status.ERROR -> {
                    hideProgress()
                    CustomDialogs.showErrorMessage(
                        this@ScheduleVisitActivity,
                        it.error?.message.toString()
                    )
                }
            }
        }
    }


    private fun getScheduledPickTimeList(): MutableList<StateCityResponse> {
        val list = mutableListOf<StateCityResponse>()
        list.add(StateCityResponse(1,"07 AM - 08 AM"))
        list.add(StateCityResponse(1,"08 AM - 09 AM"))
        list.add(StateCityResponse(1,"09 AM - 10 AM"))
        list.add(StateCityResponse(1,"10 AM - 11 AM"))
        list.add(StateCityResponse(1,"11 AM - 12 PM"))
        list.add(StateCityResponse(1,"12 PM - 01 PM"))
        list.add(StateCityResponse(1,"01 PM - 02 PM"))
        list.add(StateCityResponse(1,"02 PM - 03 PM"))
        list.add(StateCityResponse(1,"03 PM - 04 PM"))
        list.add(StateCityResponse(1,"04 PM - 05 PM"))
        list.add(StateCityResponse(1,"05 PM - 06 PM"))
        list.add(StateCityResponse(1,"06 PM - 07 PM"))
        list.add(StateCityResponse(1,"07 PM - 08 PM"))
        list.add(StateCityResponse(1,"08 PM - 09 PM"))
        list.add(StateCityResponse(1,"09 PM - 10 PM"))
        list.add(StateCityResponse(1,"10 PM - 11 PM"))
        return list
    }


}