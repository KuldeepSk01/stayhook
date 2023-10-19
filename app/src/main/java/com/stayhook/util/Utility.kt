package com.stayhook.util

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.format.DateFormat
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

object Utility{

    private var isConnect = false
    fun isConnect() = isConnect

    fun setConnection(isAvailable:Boolean){
        isConnect = isAvailable
    }

}

inline fun <reified T : Serializable> Bundle.serializable(key: String): T? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getSerializable(key) as? T
}

inline fun <reified T : Serializable> Intent.serializable(key: String): T? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializableExtra(
        key, T::class.java
    )

    else -> @Suppress("DEPRECATION") getSerializableExtra(key) as? T
}

fun View.showKeyboard() {
    requestFocusFromTouch()
    this.postDelayed({
        val im = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        im.showSoftInput(this, InputMethodManager.HIDE_NOT_ALWAYS)
    }, 2000)
}

fun View.hideKeyboard() {
    val im = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    im.hideSoftInputFromWindow(windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
}


fun getTimeFormat(hh: Int, mm: Int): String {
    val sdf = SimpleDateFormat("hh:mm")
    val mTime: Calendar = Calendar.getInstance()
    mTime.set(Calendar.HOUR, hh)
    mTime.set(Calendar.MINUTE, mm)
    return  sdf.format(mTime.time)
}
fun getDateFormat(day:Int,month:Int,year:Int): String {
    val sdf = SimpleDateFormat("EEE dd MMM yyyy")
    val mTime: Calendar = Calendar.getInstance()
    mTime.set(Calendar.DAY_OF_MONTH, day)
    mTime.set(Calendar.MONTH, month)
    mTime.set(Calendar.YEAR, year)
    return  sdf.format(mTime.time)
}


