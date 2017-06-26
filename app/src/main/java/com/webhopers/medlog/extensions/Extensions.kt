package com.webhopers.medlog.extensions

import android.widget.EditText

fun EditText.isEmpty(): Boolean {
    return text.toString().isEmpty()
}
