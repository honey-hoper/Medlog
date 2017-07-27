package com.webhopers.medlog.dialogs

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import com.webhopers.medlog.services.database.FirebaseDatabaseService

class ConfirmationDialog(context: Context, uid: String) {
    init {
        AlertDialog.Builder(context)
                .setMessage("Are you sure you want to remove this user?")
                .setPositiveButton("Remove", DialogInterface.OnClickListener { dialog, which ->
                    FirebaseDatabaseService.removeUser(uid)
                    dialog.dismiss()
                })
                .setNegativeButton("Cancel", null)
                .create()
                .show()
    }
}