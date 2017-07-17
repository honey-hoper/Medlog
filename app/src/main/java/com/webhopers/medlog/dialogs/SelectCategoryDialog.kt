package com.webhopers.medlog.dialogs

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.widget.ArrayAdapter

class SelectCategoryDialog(context: Context, callback: (list: List<String>) -> Unit) {
    init {
        val arr = arrayOf("Section Form Wise Product", "Classification Wise Product")

        AlertDialog.Builder(context)
                .setAdapter(
                        ArrayAdapter(
                                context,
                                android.R.layout.simple_list_item_1,
                                arr),
                        DialogInterface.OnClickListener { dialog, which ->
                            SelectTypeDialog(context, which, callback)
                            dialog.dismiss()
                        })
                .setTitle("Move to")
                .create()
                .show()
    }

}