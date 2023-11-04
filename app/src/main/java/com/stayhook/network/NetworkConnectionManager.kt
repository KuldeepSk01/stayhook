package com.stayhook.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.util.Log
import com.stayhook.util.Utility.setConnection


class NetworkConnectionManager(val context: Context) {
    init {
        networkManager(context)
    }

    private fun networkManager(context: Context) {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        cm?.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                setConnection(true)
                Log.d("Network", "onAvailable: $network")

            }

            override fun onLosing(network: Network, maxMsToLive: Int) {
                super.onLosing(network, maxMsToLive)
                setConnection(false)
                Log.d("Network", "onLosing: $network")

            }

            override fun onLost(network: Network) {
                super.onLost(network)
                setConnection(false)
                Log.d("Network", "onLost: $network")

            }

            override fun onUnavailable() {
                super.onUnavailable()
                setConnection(false)
                Log.d("Network", "onUnavailable")


            }
        })
    }


    /*    override fun onAvailable(network: Network) {
            super.onAvailable(network)
            setConnection(true)
            Log.d("Connection", "onAvailable: true")
        }

        override fun onLost(network: Network) {
            setConnection(false)
            Log.d("Connection", "onAvailable: false")
            super.onLost(network)
        }*/
}