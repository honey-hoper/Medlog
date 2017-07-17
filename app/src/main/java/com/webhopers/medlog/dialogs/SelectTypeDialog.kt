package com.webhopers.medlog.dialogs

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import com.webhopers.medlog.R

class SelectTypeDialog(context: Context,
                       position: Int,
                       callback: (list: List<String>) -> Unit,
                       val selectedItems: HashSet<String> = HashSet<String>()) {
    init {
        val arr = if (position == 0) context.resources.getStringArray(R.array.product_types)
                  else context.resources.getStringArray(R.array.product_for)

        AlertDialog.Builder(context)
                .setTitle("Move to")
                .setMultiChoiceItems(arr, null, DialogInterface.OnMultiChoiceClickListener { dialog, which, isChecked ->
                    if (isChecked) selectedItems.add(arr[which])
                    if (!isChecked && selectedItems.contains(arr[which])) selectedItems.remove(arr[which])
                })
                .setPositiveButton("Move", DialogInterface.OnClickListener { dialog, which ->
                    callback(selectedItems.toList())
                    dialog.dismiss()
                })
                .create()
                .show()
    }

}