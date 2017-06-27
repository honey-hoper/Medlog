package com.webhopers.medlog.extensions

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText

fun EditText.isEmpty(): Boolean {
    return text.toString().isEmpty()
}

fun Context.inflateView(id: Int): View {
    val inflator = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val v = inflator.inflate(id, null)
    return v
}
