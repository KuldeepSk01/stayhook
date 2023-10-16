package com.stayhook.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.stayhook.R
import com.stayhook.databinding.CustomDialogSuccessLayoutBinding


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

}

