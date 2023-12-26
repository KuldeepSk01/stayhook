package com.stayhook.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.Calendar

object Utility {

    private var isConnect = false
    fun isConnectionAvailable() = isConnect
    fun setConnection(isAvailable: Boolean) {
        isConnect = isAvailable
    }

}

fun mLog(msg:String){
    Log.d("StayHook",msg)
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
    return sdf.format(mTime.time)
}

fun getSelectedDateForApi(date:String): String {
    val sdf = SimpleDateFormat("MMM dd,yyyy")
   val nDate =  sdf.parse(date)
    val c = Calendar.getInstance()
    c.time = nDate!!
    val sdf1 = SimpleDateFormat("yyyy-MM-dd")
    return sdf1.format(c.time)

}

fun getCurrentDate(): String {
    val sdf = SimpleDateFormat("MMM dd,yyyy")
    val currentDate = Calendar.getInstance().time
    return sdf.format(currentDate)
}

fun getDateFormat(day: Int, month: Int, year: Int): String {
    //  val sdf = SimpleDateFormat("EEE dd MMM yyyy")
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    val mTime: Calendar = Calendar.getInstance()
    mTime.set(Calendar.DAY_OF_MONTH, day)
    mTime.set(Calendar.MONTH, month)
    mTime.set(Calendar.YEAR, year)
    return sdf.format(mTime.time)
}

fun selectDateFormat(day: Int, month: Int, year: Int): String {
    //  val sdf = SimpleDateFormat("EEE dd MMM yyyy")
    val sdf = SimpleDateFormat("MMM dd,yyyy")
    val mTime: Calendar = Calendar.getInstance()
    mTime.set(Calendar.DAY_OF_MONTH, day)
    mTime.set(Calendar.MONTH, month)
    mTime.set(Calendar.YEAR, year)
    return sdf.format(mTime.time)
}


fun removeAllFragmentsFromFragment(fragment: Fragment) {
    val fm = fragment.requireActivity().supportFragmentManager
    for (i in 0 until fm.backStackEntryCount) {
        fm.popBackStack()
    }
    // onBackPress()
}


fun getRealPathFromURI(uri: Uri, activity: Activity): String? {
    val returnCursor: Cursor = activity.applicationContext.contentResolver.query(
        uri, null, null, null, null
    )!!
    val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
    returnCursor.moveToFirst()
    val name = returnCursor.getString(nameIndex)
    val file: File = File(activity.applicationContext.filesDir, name)
    try {
        val inputStream: InputStream =
            activity.applicationContext.contentResolver.openInputStream(uri)!!
        val outputStream = FileOutputStream(file)
        var read = 0
        val maxBufferSize = 1 * 1024 * 1024
        val bytesAvailable = inputStream.available()
        val bufferSize = Math.min(bytesAvailable, maxBufferSize)
        val buffers = ByteArray(bufferSize)
        while (inputStream.read(buffers).also { read = it } != -1) {
            outputStream.write(buffers, 0, read)
        }
        inputStream.close()
        outputStream.close()
        Log.e("File Path", "Path " + file.absolutePath)
    } catch (e: java.lang.Exception) {
        Log.e("Exception", e.message!!)
    }
    return file.absolutePath
}


interface OnDropDownListener {
    fun onDropDownClick(item: String)
}

fun dropDownPopup(
    context: Context, isBelow: View, menuLayout: Int, listener: OnDropDownListener
): PopupMenu {
    val popup = PopupMenu(context, isBelow)
    popup.menuInflater.inflate(menuLayout, popup.menu)
    popup.setOnMenuItemClickListener { item ->
        listener.onDropDownClick(item.title.toString())
        true
    }
    return popup
}




