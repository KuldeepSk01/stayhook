package com.stayhook.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.util.Log
import com.stayhook.util.setConnection

class CheckConnection(val context: Context) : ConnectivityManager.NetworkCallback() {


    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        setConnection(true)
        Log.d("Connection", "onAvailable: true")
    }

    override fun onLost(network: Network) {
        setConnection(false)
        Log.d("Connection", "onAvailable: false")
        super.onLost(network)
    }
}