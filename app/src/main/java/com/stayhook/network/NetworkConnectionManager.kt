package com.stayhook.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import com.stayhook.util.Utility.setConnection


class NetworkConnectionManager(val context: Context) {
    init {
        networkManager(context)
    }

    private fun networkManager(context: Context) {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()
        cm?.registerNetworkCallback(networkRequest, object : ConnectivityManager.NetworkCallback() {
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

}