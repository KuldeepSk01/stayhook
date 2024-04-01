package com.stayhook.util

import android.app.Activity
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import com.andrognito.flashbar.Flashbar
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.stayhook.R
import com.stayhook.databinding.CustomDialogSuccessLayoutBinding
import com.stayhook.databinding.DailogProgressLayoutBinding
import com.stayhook.databinding.DateDialougeLayoutBinding
import com.stayhook.databinding.TimeDialougeLayoutBinding


object CustomDialogs {

    interface CustomDialogsListener {
        fun onComplete(d: Dialog)
    }

    fun showCustomSuccessDialog(
        context: Context,
        title: String,
        subTitle: String,
        btnName: String,
        listener: CustomDialogsListener
    ): Dialog {

        val dialog = BottomSheetDialog(context, R.style.AppBottomSheetDialogTheme)
        val dB = DataBindingUtil.inflate<CustomDialogSuccessLayoutBinding>(
            LayoutInflater.from(context),
            R.layout.custom_dialog_success_layout,
            null,
            false
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dB.tvDialogTitle.text = title
        dB.tvDialogSubTitle.text = subTitle
        dB.tvDialogOkayBtn.apply {
            text = btnName
            setOnClickListener {
                listener.onComplete(dialog)
            }
        }
        dialog.setCancelable(false)
        dialog.setContentView(dB.root)
        dialog.create()
        return dialog
    }


    fun successProgressDialog(context: Context): Dialog {
        val dialog = Dialog(context)
        val dB = DataBindingUtil.inflate<DailogProgressLayoutBinding>(
            LayoutInflater.from(context),
            R.layout.dailog_progress_layout,
            null,
            false
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setGravity(Gravity.CENTER)
        dialog.setCancelable(false)
        dialog.setContentView(dB.root)
        dialog.create()
        return dialog
    }

    fun showErrorMessage(context: Activity, message: String) {
        val flashBar = Flashbar.Builder(context)
        flashBar.gravity(Flashbar.Gravity.TOP)
        flashBar.message(message)
        flashBar.backgroundColor(
            ResourcesCompat.getColor(
                context.resources,
                R.color.error_color,
                null
            )
        )
        flashBar.duration(3000)
        flashBar.title(R.string.app_name)
        flashBar.build()
        flashBar.show()
    }

    fun showSuccessMessage(context: Activity, message: String) {
        val flashBar = Flashbar.Builder(context)
        flashBar.gravity(Flashbar.Gravity.TOP)
        flashBar.message(message)
        flashBar.titleColor(ResourcesCompat.getColor(context.resources, R.color.light_black, null))
        flashBar.messageColor(
            ResourcesCompat.getColor(
                context.resources,
                R.color.light_black,
                null
            )
        )
        flashBar.backgroundColor(ResourcesCompat.getColor(context.resources, R.color.gray4, null))
        flashBar.duration(3000)
        flashBar.title(R.string.app_name)
        flashBar.build()
        flashBar.show()
    }



    @RequiresApi(Build.VERSION_CODES.O)
    fun showPickerDateDialog(context: Context, listener: OnShowDateDialogListener) {
        val dialog = Dialog(context)
        val b = DataBindingUtil.inflate<DateDialougeLayoutBinding>(
            LayoutInflater.from(context),
            R.layout.date_dialouge_layout,
            null,
            false
        )
        b.calenderViewDialog.minDate = System.currentTimeMillis()-1000
        b.calenderViewDialog.setOnDateChangedListener { datePicker_, i, i2, i3 ->
            listener.onSelectDate(selectDateFormat(i3, i2, i))
            dialog.dismiss()
        }


        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)

        dialog.setContentView(b.root)
        dialog.create()
        dialog.show()
    }



    @RequiresApi(Build.VERSION_CODES.O)
    fun showDateDialog(context: Context, listener: OnShowDateDialogListener) {
        val dialog = Dialog(context)
        val b = DataBindingUtil.inflate<DateDialougeLayoutBinding>(
            LayoutInflater.from(context),
            R.layout.date_dialouge_layout,
            null,
            false
        )
        //b.calenderViewDialog.minDate = System.currentTimeMillis()-1000
        b.calenderViewDialog.setOnDateChangedListener { datePicker_, i, i2, i3 ->
            listener.onSelectDate(getDateFormat(i3, i2, i))
            dialog.dismiss()
        }


        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)

        dialog.setContentView(b.root)
        dialog.create()
        dialog.show()
    }

    fun showTimePickerDialog(context: Context, listener: OnTimePickerDialogListener) {
        val dialog = Dialog(context)
        val b = DataBindingUtil.inflate<TimeDialougeLayoutBinding>(
            LayoutInflater.from(context),
            R.layout.time_dialouge_layout,
            null,
            false
        )
        b.calenderViewDialog.setIs24HourView(true)
        b.calenderViewDialog.setOnTimeChangedListener(object : TimePicker.OnTimeChangedListener {
            override fun onTimeChanged(p0: TimePicker?, p1: Int, p2: Int) {
                listener.onSelectTime(getTimeFormat(p1,p2))
             //   dialog.dismiss()
            }

        })

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setContentView(b.root)
        dialog.create()
        dialog.show()
    }


    interface OnShowDateDialogListener {
        fun onSelectDate(date: String)
    }

    interface OnTimePickerDialogListener {
        fun onSelectTime(time: String)
    }

}

