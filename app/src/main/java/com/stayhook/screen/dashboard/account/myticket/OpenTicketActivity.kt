package com.stayhook.screen.dashboard.account.myticket

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.Gravity
import android.view.LayoutInflater
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.stayhook.R
import com.stayhook.adapter.TicketImagesAdapter
import com.stayhook.base.BaseActivity
import com.stayhook.databinding.ActivityOpenTicketBinding
import com.stayhook.databinding.DialogTicketCommentBinding
import com.stayhook.databinding.DialogTicketShceduleAMeetBinding
import com.stayhook.model.Ticket
import com.stayhook.util.Constants
import com.stayhook.util.CustomDialogs
import com.stayhook.util.mLog
import com.stayhook.util.serializable

class OpenTicketActivity : BaseActivity() {
    private lateinit var mBind: ActivityOpenTicketBinding
    override val layoutId: Int
        get() = R.layout.activity_open_ticket

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewInit(binding: ViewDataBinding?) {
        mBind = binding as ActivityOpenTicketBinding
        val model = intent.getBundleExtra(Constants.DefaultConstants.BUNDLE)
            ?.serializable<Ticket>(Constants.DefaultConstants.MODEL_DETAIL)
        mBind.apply {
            toolbarOpenTicket.apply {
                ivToolBarBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
                tvToolBarTitle.text = model!!.status
            }

            rvTicketImages.apply {
                layoutManager  = LinearLayoutManager(this@OpenTicketActivity,LinearLayoutManager.HORIZONTAL,false)
                adapter = TicketImagesAdapter(getImages(),this@OpenTicketActivity)
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
        val dialog = Dialog(context,android.R.style.Theme_Material)
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
        val dialog = Dialog(context,android.R.style.Theme_Material)
        val dB = DataBindingUtil.inflate<DialogTicketShceduleAMeetBinding>(
            LayoutInflater.from(context),
            R.layout.dialog_ticket_shcedule_a_meet,
            null,
            false
        )
        dB.apply {
            tvSelectDate.setOnClickListener {
                CustomDialogs.showDateDialog(context, object : CustomDialogs.OnShowDateDialogListener{
                    override fun onSelectDate(date: String) {
                        tvSelectDate.text  = date
                    }
                })
            }

            tvScheduleDialogBtn.setOnClickListener {
                dialog.dismiss()
            }
        }
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setContentView(dB.root)
        dialog.create()
        return dialog
    }

    private fun getImages():MutableList<String>{
        val list = mutableListOf<String>()
        list.add("https://content.jdmagicbox.com/comp/def_content/plumbers/vishal-plumbing-contractor-raj-nagar-extension-ghaziabad-ghaziabad-plumbing-contractors-6.jpg?clr=")
        list.add("https://api.gharpedia.com/wp-content/uploads/2018/08/0602030005-01-Plumbers.jpg")
        list.add("https://suburbanplumbingoc.com/wp-content/uploads/2020/06/How-Your-Home-Plumbing-System-Works.jpg")
        list.add("https://content.jdmagicbox.com/comp/def_content/plumbers/vishal-plumbing-contractor-raj-nagar-extension-ghaziabad-ghaziabad-plumbing-contractors-6.jpg?clr=")
        list.add("https://suburbanplumbingoc.com/wp-content/uploads/2020/06/How-Your-Home-Plumbing-System-Works.jpg")
        list.add("https://api.gharpedia.com/wp-content/uploads/2018/08/0602030005-01-Plumbers.jpg")

        return list
    }

}

