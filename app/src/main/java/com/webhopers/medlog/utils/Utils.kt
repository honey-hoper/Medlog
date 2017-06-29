package com.webhopers.medlog.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.util.TypedValue
import java.net.InetAddress
import java.net.UnknownHostException
import kotlin.properties.Delegates

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

fun convertDpToPixels(dip: Float, r: Resources) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, r.displayMetrics)

fun cropSquare(bitmap: Bitmap): Bitmap {
    var x by Delegates.notNull<Int>()
    var y by Delegates.notNull<Int>()

    if (bitmap.width == bitmap.height) {
        x = bitmap.width
        y = bitmap.width
    } else {
        x = maxOf(bitmap.width, bitmap.height)
        y = minOf(bitmap.width, bitmap.height)
    }

    val crop = (x - y) / 2
    if (bitmap.width > bitmap.height) return Bitmap.createBitmap(bitmap, crop, 0, y, y)
    else return Bitmap.createBitmap(bitmap, 0, crop, y, y)
}