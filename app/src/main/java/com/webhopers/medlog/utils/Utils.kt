package com.webhopers.medlog.utils

import android.content.Context
import android.net.ConnectivityManager
import java.net.InetAddress
import java.net.UnknownHostException

fun isConnected(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return cm.activeNetworkInfo != null && cm.activeNetworkInfo.isConnected
}

fun isInternetAvailable(context: Context): Boolean {
    if (!isConnected(context))
        return false

    try {
        val address = InetAddress.getByName("www.google.com")
        return !address.equals("")
    } catch (e: UnknownHostException) {}

    return false
}