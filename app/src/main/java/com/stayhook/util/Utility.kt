package com.stayhook.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.PopupMenu
import android.widget.Toast
//import androidx.fragment.app.Fragment
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.Serializable
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object Utility {

    private var isConnect = false
    fun isConnectionAvailable() = isConnect
    fun setConnection(isAvailable: Boolean) {
        isConnect = isAvailable
    }


    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
            true
        } else {
            false
        }
    }

}

fun mLog(msg:String){
    Log.d("StayHook",msg)
}
fun mToast(context:Context,msg:String){
    Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
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


fun getCurrentTimeFormat(): String {
    val sdf = SimpleDateFormat("HH:mm a")
    val mTime: Calendar = Calendar.getInstance()
    return sdf.format(mTime.time)
}
fun getTimeFormat(hh: Int, mm: Int): String {
    val sdf = SimpleDateFormat("HH:mm a")
    val mTime: Calendar = Calendar.getInstance()
    mTime.set(Calendar.HOUR, hh)
    mTime.set(Calendar.MINUTE, mm)
    mTime.set(Calendar.MINUTE, mm)
    return sdf.format(mTime.time)
}
fun setMonthYearDateFormat(date:String): String {
    val sdf = SimpleDateFormat("yyyy-MM")
    val nDate =  sdf.parse(date)
    val c = Calendar.getInstance()
    c.time = nDate!!
    val sdf1 = SimpleDateFormat("MMM,yyyy")
    return sdf1.format(c.time)
}
fun setDateFormat(date:String): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    val nDate =  sdf.parse(date)
    val c = Calendar.getInstance()
    c.time = nDate!!
    val sdf1 = SimpleDateFormat("MMM dd,yyyy")
    return sdf1.format(c.time)
}

fun getCurrentDate(): String {
    val sdf = SimpleDateFormat("dd MMM yyyy")
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
    val sdf = SimpleDateFormat("dd MMM yyyy")
    val mTime: Calendar = Calendar.getInstance()
    mTime.set(Calendar.DAY_OF_MONTH, day)
    mTime.set(Calendar.MONTH, month)
    mTime.set(Calendar.YEAR, year)
    return sdf.format(mTime.time)
}


/*
fun removeAllFragmentsFromFragment(fragment: Fragment) {
    val fm = fragment.requireActivity().supportFragmentManager
    for (i in 0 until fm.backStackEntryCount) {
        fm.popBackStack()
    }
    // onBackPress()
}
*/


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

private fun compressImageFilePath(bm: Bitmap, context: Context): String {
    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val file = File(context.filesDir, timeStamp + ".png")
    if (file.exists()) {
        file.delete()
    }
    try {
        file.createNewFile()
    } catch (e: IOException) {
        e.printStackTrace()
    }
    var fos: FileOutputStream? = null
    try {
        fos = FileOutputStream(file)
        bm.compress(Bitmap.CompressFormat.JPEG, 50, fos)

    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        try {
            assert(fos != null)
            fos!!.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    return file.path
}



@Throws(ParseException::class)
fun formatDate(date: String, initDateFormat: String, endDateFormat: String): String {
    return if (date.isNotEmpty()) {
        val initDate = SimpleDateFormat(initDateFormat, Locale.getDefault()).parse(date)
        val formatter = SimpleDateFormat(endDateFormat, Locale.getDefault())
        formatter.format(initDate)
    } else {
        ""
    }
}

/*
@Throws(ParseException::class)
fun commonFormatDate(date: String, initDateFormat: String="dd MMM yyyy", endDateFormat: String="yyyy-mm-dd"): String {
    return if (date.isNotEmpty()) {
        val initDate = SimpleDateFormat(initDateFormat, Locale.getDefault()).parse(date)
        val formatter = SimpleDateFormat(endDateFormat, Locale.getDefault())
        formatter.format(initDate)
    } else {
        ""
    }
}

*/


