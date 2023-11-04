package com.stayhook.util

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ScrollView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import com.andrognito.flashbar.Flashbar
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.SnackbarLayout
import com.stayhook.R
import com.stayhook.databinding.CustomDialogSuccessLayoutBinding
import com.stayhook.databinding.DailogProgressLayoutBinding
import com.stayhook.databinding.DialogMessageBinding


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
        flashBar.backgroundColor(ResourcesCompat.getColor(context.resources,R.color.error_color,null))
        flashBar.duration(3000)
        flashBar.title(R.string.app_name)
        flashBar.build()
        flashBar.show()
    }
    fun showSuccessMessage(context: Activity, message: String) {
        val flashBar = Flashbar.Builder(context)
        flashBar.gravity(Flashbar.Gravity.TOP)
        flashBar.message(message)
        flashBar.titleColor(ResourcesCompat.getColor(context.resources,R.color.light_black,null))
        flashBar.messageColor(ResourcesCompat.getColor(context.resources,R.color.light_black,null))
        flashBar.backgroundColor(ResourcesCompat.getColor(context.resources,R.color.gray4,null))
        flashBar.duration(3000)
        flashBar.title(R.string.app_name)
        flashBar.build()
        flashBar.show()
    }

}

