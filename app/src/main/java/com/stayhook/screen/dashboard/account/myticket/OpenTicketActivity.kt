package com.stayhook.screen.dashboard.account.myticket

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.LayoutInflater
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.stayhook.R
import com.stayhook.adapter.TicketImagesAdapter
import com.stayhook.adapter.TicketStatusAdapter
import com.stayhook.base.BaseActivity
import com.stayhook.base.BaseResponse
import com.stayhook.databinding.ActivityOpenTicketBinding
import com.stayhook.databinding.DialogTicketCommentBinding
import com.stayhook.databinding.DialogTicketShceduleAMeetBinding
import com.stayhook.databinding.DialogZoomImageLayoutBinding
import com.stayhook.model.TicketImagesModel
import com.stayhook.model.request.AddTicketScheduleMeetRequest
import com.stayhook.model.response.SuccessErrorResponse
import com.stayhook.model.response.TicketDetailResponse
import com.stayhook.model.response.TicketImage
import com.stayhook.model.response.TicketJourney
import com.stayhook.model.response.TicketResponse
import com.stayhook.network.ApiResponse
import com.stayhook.screen.dashboard.account.myticket.create.CreateTicketViewModel
import com.stayhook.util.Constants
import com.stayhook.util.CustomDialogs
import com.stayhook.util.getCurrentDate
import com.stayhook.util.getCurrentTimeFormat
import com.stayhook.util.mLog
import com.stayhook.util.mToast
import com.stayhook.util.serializable
import org.koin.core.component.inject

class OpenTicketActivity : BaseActivity(), TicketImagesAdapter.OnImageClickListener {
    private lateinit var mBind: ActivityOpenTicketBinding
    private val mViewModel: CreateTicketViewModel by inject()
    private lateinit var ticketResponse: TicketResponse

    override val layoutId: Int
        get() = R.layout.activity_open_ticket

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewInit(binding: ViewDataBinding?) {
        mBind = binding as ActivityOpenTicketBinding
        ticketResponse = intent.getBundleExtra(Constants.DefaultConstants.BUNDLE)
            ?.serializable<TicketResponse>(Constants.DefaultConstants.MODEL_DETAIL)!!

        hitTicketDetailApi()

        mBind.apply {
            toolbarOpenTicket.apply {
                ivToolBarBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
                tvToolBarTitle.text = ticketResponse.status
            }

            tvCommentBtn.setOnClickListener {
                showCommentDialog(this@OpenTicketActivity).show()
            }

            tvScheduleMeetBtn.setOnClickListener {
                showScheduledMeetDialog(this@OpenTicketActivity).show()
            }

        }
    }

    private fun showCommentDialog(context: Context): Dialog {
        val dialog = Dialog(context, android.R.style.Theme_Material)
        val dB = DataBindingUtil.inflate<DialogTicketCommentBinding>(
            LayoutInflater.from(context),
            R.layout.dialog_ticket_comment,
            null,
            false
        )
        dB.apply {
            tvCommentDialogBtn.setOnClickListener {
                val comment = etCommentDialog.text.toString()
                mLog("Comment : $comment")
                dialog.dismiss()
                mViewModel.hitAddTicketCommentApi(ticketResponse.id, comment)
                mViewModel.getAddTicketCommentResponse()
                    .observe(this@OpenTicketActivity, ticketCommentResponseObserver)
            }
            ivCrossBtn.setOnClickListener {
                dialog.dismiss()
            }
        }
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setContentView(dB.root)
        dialog.create()
        return dialog
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showScheduledMeetDialog(context: Context): Dialog {
        val dialog = Dialog(context, android.R.style.Theme_Material)
        val dB = DataBindingUtil.inflate<DialogTicketShceduleAMeetBinding>(
            LayoutInflater.from(context),
            R.layout.dialog_ticket_shcedule_a_meet,
            null,
            false
        )
        dB.apply {
            tvSelectDate.apply {
                text  = getCurrentDate()
                setOnClickListener {
                    CustomDialogs.showPickerDateDialog(
                        context,
                        object : CustomDialogs.OnShowDateDialogListener {
                            override fun onSelectDate(date: String) {
                                tvSelectDate.text = date
                            }
                        })
                }
            }

            tvSelectTime.apply {
                text = getCurrentTimeFormat()
                setOnClickListener {
                    CustomDialogs.showTimePickerDialog(
                        context,
                        object : CustomDialogs.OnTimePickerDialogListener{
                            override fun onSelectTime(time: String) {
                                tvSelectTime.text = time
                            }
                        })
                }
            }
            tvScheduleDialogBtn.setOnClickListener {
                dialog.dismiss()

                val req = AddTicketScheduleMeetRequest().apply {
                    ticket_id = ticketResponse.id
                    meeting_date = tvSelectDate.text.toString()
                    meeting_time = tvSelectTime.text.toString()
                    comment = etDescription.text.toString()
                }

                mViewModel.hitAddTicketScheduleMeetApi(req)
                mViewModel.getAddTicketScheduleMeetResponse()
                    .observe(this@OpenTicketActivity, ticketScheduleMeetResponseObserver)
            }
            ivCrossBtn.setOnClickListener {
                dialog.dismiss()
            }
        }
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setContentView(dB.root)
        dialog.create()
        return dialog
    }

/*
    private fun getImages(): MutableList<TicketImagesModel> {
        val list = mutableListOf<TicketImagesModel>()
        list.add(
            TicketImagesModel(
                "",
                "https://content.jdmagicbox.com/comp/def_content/plumbers/vishal-plumbing-contractor-raj-nagar-extension-ghaziabad-ghaziabad-plumbing-contractors-6.jpg?clr="
            )
        )
        list.add(
            TicketImagesModel(
                "",
                "https://api.gharpedia.com/wp-content/uploads/2018/08/0602030005-01-Plumbers.jpg"
            )
        )
        list.add(
            TicketImagesModel(
                "",
                "https://suburbanplumbingoc.com/wp-content/uploads/2020/06/How-Your-Home-Plumbing-System-Works.jpg"
            )
        )
        list.add(
            TicketImagesModel(
                "",
                "https://api.gharpedia.com/wp-content/uploads/2018/08/0602030005-01-Plumbers.jpg"
            )
        )
        list.add(
            TicketImagesModel(
                "",
                "https://content.jdmagicbox.com/comp/def_content/plumbers/vishal-plumbing-contractor-raj-nagar-extension-ghaziabad-ghaziabad-plumbing-contractors-6.jpg?clr="
            )
        )
        return list
    }
*/

    private val ticketScheduleMeetResponseObserver =
        Observer<ApiResponse<SuccessErrorResponse>> {
            when (it.status) {
                ApiResponse.Status.LOADING -> {
                    showProgress()
                }

                ApiResponse.Status.SUCCESS -> {
                    hideProgress()
                    mToast(this@OpenTicketActivity, it.data?.message.toString())
                    hitTicketDetailApi()

                }

                ApiResponse.Status.ERROR -> {
                    hideProgress()
                    mToast(this@OpenTicketActivity, it.error?.message.toString())
                }
            }
        }


    private val ticketCommentResponseObserver =
        Observer<ApiResponse<SuccessErrorResponse>> {
            when (it.status) {
                ApiResponse.Status.LOADING -> {
                    showProgress()
                }

                ApiResponse.Status.SUCCESS -> {
                    hideProgress()
                    mToast(this@OpenTicketActivity, it.data?.message.toString())
                    hitTicketDetailApi()
                }

                ApiResponse.Status.ERROR -> {
                    hideProgress()
                    mToast(this@OpenTicketActivity, it.error?.message.toString())
                }
            }
        }


    private val ticketDetailResponseObserver =
        Observer<ApiResponse<BaseResponse<TicketDetailResponse>>> {
            when (it.status) {
                ApiResponse.Status.LOADING -> {
                    showProgress()
                }

                ApiResponse.Status.SUCCESS -> {
                    hideProgress()
                    setDetail(it.data!!.data)

                }

                ApiResponse.Status.ERROR -> {
                    hideProgress()
                    mToast(this@OpenTicketActivity, it.error?.message.toString())
                }
            }
        }

    private fun setDetail(model: TicketDetailResponse) {
        mBind.apply {
            //setTicketImage(model.ticket_journey as MutableList<TicketImage>)
            Glide.with(this@OpenTicketActivity).load(model.property_image)
                .placeholder(R.drawable.default_image).into(ivItemTicketImg)
            tvItemTicketProperty.text = model.property_name
            tvItemTicketTenantName.text = model.tenant_name
            tvItemTicketTenantType.text = model.issue.trim()
            tvItemTicketTenantIssueType.text = model.issue_type.trim()
            tvItemTicketTenantIssueDetail.text = model.issue_details.trim()

            setTicketStatus(model.ticket_journey as MutableList<TicketJourney>)


        }

    }

    private fun setTicketStatus(list: MutableList<TicketJourney>) {
        mBind.apply {
            rvTicketStatus.apply {
                layoutManager = LinearLayoutManager(
                    this@OpenTicketActivity,
                    LinearLayoutManager.VERTICAL,
                    true
                )
                adapter = TicketStatusAdapter(list, this@OpenTicketActivity,this@OpenTicketActivity)
            }
        }

    }

    private fun setTicketImage(ticket: MutableList<TicketImage>) {
        val list = mutableListOf<TicketImagesModel>()
        ticket.forEach {
            list.add(TicketImagesModel("", it.image))
        }

        mBind.rvTicketImages.apply {
            layoutManager =
                LinearLayoutManager(this@OpenTicketActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = TicketImagesAdapter(list, this@OpenTicketActivity, false,this@OpenTicketActivity)

        }
    }


    private fun hitTicketDetailApi(){
        mViewModel.hitGetTicketDetailApi(ticketResponse.id)
        mViewModel.getTicketDetailResponse()
            .observe(this@OpenTicketActivity, ticketDetailResponseObserver)
    }

    override fun onClickImage(model: TicketImagesModel) {
        showImageDialog(model.url,this@OpenTicketActivity)
    }

    private fun showImageDialog(url: String,context: Context) {
        val dialog = Dialog(this,android.R.style.Theme_Material)
        val binding = DataBindingUtil.inflate<DialogZoomImageLayoutBinding>(LayoutInflater.from(context),R.layout.dialog_zoom_image_layout,null,false,)

        binding.apply {
            ivCrossBtn.setOnClickListener { dialog.dismiss() }
            Glide.with(context).load(url).placeholder(R.drawable.default_image).into(ivZoomImage)
        }

        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setContentView(binding.root)
        dialog.create()
        dialog.show()

    }


    /*  override fun onResume() {
          mViewModel.hitGetTicketDetailApi(ticketResponse.id)
          mViewModel.getTicketDetailResponse()
              .observe(this@OpenTicketActivity, ticketDetailResponseObserver)
          super.onResume()
      }

      override fun onPause() {

          super.onPause()
      }
  */
}

