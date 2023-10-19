package com.stayhook.application

import android.app.Application
import android.util.Log
import android.widget.Toast
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.stayhook.koin.appModule
import com.stayhook.koin.repositoryModule
import com.stayhook.koin.viewModelModule
import com.stayhook.network.CheckConnection
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin

class StayHookApplication : Application(), KoinComponent {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@StayHookApplication)
            modules(listOf(appModule, repositoryModule, viewModelModule))
        }
        CheckConnection(applicationContext)
        if (checkGooglePlayService()){
            Log.d("TAG", "onCreate: google play service is Available...")
        }else{
            Log.d("TAG", "onCreate: google play service is UnAvailable...")
        }

    }

    private fun checkGooglePlayService(): Boolean {
        val googleAvailability = GoogleApiAvailability.getInstance()
        val result = googleAvailability.isGooglePlayServicesAvailable(this@StayHookApplication)
        if (result == ConnectionResult.SUCCESS) {
            return true
        } else if (googleAvailability.isUserResolvableError(result)
        ) {
            /* val dialog :Dialog = googleAvailability.getErrorDialog(this,result,201,object:DialogInterface.OnCancelListener{
                 override fun onCancel(p0: DialogInterface?) {
                     Toast.makeText(this@StayHookApplication, "User cancel the dialog", Toast.LENGTH_SHORT).show()
                     p0?.dismiss()
                 }
             })
             dialog.show()*/
        }
        return false
    }
}