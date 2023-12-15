package com.stayhook.permissions

import android.Manifest
import android.content.Context
import android.util.Log
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import com.stayhook.util.mLog

object MyPermissions {

    var isLocationEnable: Boolean = false
    var isReadStorageEnable: Boolean = false
    var isCameraAccessEnable: Boolean = false


    fun requestLocationPermission(context:Context):Boolean{
        var locationPermission = false


        return locationPermission
    }

/*
    private fun setGpsProvider(context: Context) {
//            val manager = getSystemService(Context.LOCATION_SERVICE) as LocationManager?
//            if (!manager?.isProviderEnabled(LocationManager.GPS_PROVIDER)!!) {
//                isGpEnable = true
//                buildAlertMessageNoGps(context);
//            }else{
//                isGpEnable = true
//            }
    }
*/
/*
    fun getLocationPermission(context: Context) {

    Dexter.withContext(context).withPermissions(
        arrayListOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    ).withListener(object : MultiplePermissionsListener {
        override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
            if (p0?.areAllPermissionsGranted()!!) {
                mLog("Location Permission grated")
                isLocationEnable = true
            } else {
                mLog("Location Permission Denied !")
            }
        }
        override fun onPermissionRationaleShouldBeShown(
            p0: MutableList<PermissionRequest>?,
            p1: PermissionToken?
        ) {
            p1?.continuePermissionRequest()
            mLog("Location Permission should be granted...")

        }

    }).withErrorListener {
        mLog("Location Permission Error ${it.name}")
        isLocationEnable = false
    }.check()


      */
/*  Dexter.withContext(context).withPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    isLocationEnable = true
                    Log.d("LocationPermission", "onPermissionGranted: Granted..")
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    Log.d("LocationPermission", "onPermissionGranted: Denied..")
                    isLocationEnable = false
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    p1: PermissionToken?
                ) {
                    isLocationEnable = false
                    Log.d("LocationPermission", "onPermissionGranted: RationalShould")
                }

            }).check()
*//*

    }
*/




    fun getReadStoragePermission(context: Context) {
        Dexter.withContext(context)
            .withPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    isReadStorageEnable = true
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    isReadStorageEnable = false
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    p1: PermissionToken?
                ) {
                    isReadStorageEnable = false
                }
            }).check()


    }

    fun getCameraAccessPermission(context: Context) {
        Dexter.withContext(context).withPermission(android.Manifest.permission.CAMERA)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    isCameraAccessEnable = true
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    isCameraAccessEnable = false
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    p1: PermissionToken?
                ) {
                    isCameraAccessEnable = false
                }
            }).check()


    }

    //private var currentLocation: Location? = null
    ////lateinit var locationManager: LocationManager
    //locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

   // val hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    //------------------------------------------------------//
   // val hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)



}