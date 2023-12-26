package com.stayhook.screen.dashboard

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.util.Log
import androidx.core.app.ActivityCompat
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.stayhook.application.StayHookApplication
import com.stayhook.base.BaseViewModel
import com.stayhook.permissions.MyPermissions
import com.stayhook.util.Constants
import com.stayhook.util.mLog

class MainViewModel(private val context: StayHookApplication) : BaseViewModel() {
    private val LOCATION_UPDATE_TIME = 60000L   //60sec
    lateinit var locationManager: LocationManager

    fun checkLocationPermission() {
        mPref.put(Constants.PreferenceConstant.LATITUDE, 0f)
        mPref.put(Constants.PreferenceConstant.LONGITUDE, 0f)
        locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        Dexter.withContext(context).withPermissions(
            arrayListOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        ).withListener(object : MultiplePermissionsListener {
            override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
                if (p0?.areAllPermissionsGranted()!!) {
                    mLog("Permission grated")
                    gpsInit()
                } else {
                    mLog("Permission not grated")
                }
            }
            override fun onPermissionRationaleShouldBeShown(
                p0: MutableList<PermissionRequest>?,
                p1: PermissionToken?
            ) {
                p1?.continuePermissionRequest()
                mLog("Permission should be granted...")

            }

        }).withErrorListener {
            mLog("Permission Error ${it.name}")
        }.check()


/*
        if (MyPermissions.requestLocationPermission(context)) {
            mLog("Location Permission granted..")
        } else {
            mLog("Location Permission denied..")
        }*/
    }

    private fun gpsInit() {
        if (ActivityCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        } else {
            val hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            val locationGSP1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            locationGSP1?.let {
                Log.d("TAG", "Main ViewModel lat ${it.latitude} lng ${it.longitude}")
                mPref.put(Constants.PreferenceConstant.LATITUDE, it.latitude.toFloat())
                mPref.put(Constants.PreferenceConstant.LONGITUDE, it.longitude.toFloat())
            }

            if (hasGps) {
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    LOCATION_UPDATE_TIME,
                    0F
                ) { p0 ->
                    p0.let {
                        Log.d(
                            "TAG",
                            "Main ViewModel updated lat ${it.latitude} lng ${it.longitude}"
                        )
                        mPref.put(
                            Constants.PreferenceConstant.LATITUDE, it.latitude.toFloat()
                        )
                        mPref.put(
                            Constants.PreferenceConstant.LONGITUDE, it.longitude.toFloat()
                        )
                    }
                }
            }

        }


    }

}