package com.webhopers.medlog.dialogs

import android.app.AlertDialog
import android.content.Context
import com.webhopers.medlog.R
import com.webhopers.medlog.extensions.inflateView
import io.realm.exceptions.RealmPrimaryKeyConstraintException
import kotlinx.android.synthetic.main.playlist_name_dialog.*

class PlaylistNameDialog(context: Context, callback: (String) -> Unit) {
    init {
        AlertDialog.Builder(context)
                .setView(context.inflateView(R.layout.playlist_name_dialog))
                .setPositiveButton(R.string.create, null)
                .setNegativeButton(R.string.cancel, null)
                .create()
                .apply {
                    show()
                    getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                        val playlistName = playlist_name_dialog_edit_text.text.toString()

                        if (playlistName.isEmpty()) {
                            playlist_name_dialog_edit_text.setError("Empty")
                            return@setOnClickListener
                        }
                        try {
                            callback(playlistName)
                            dismiss()
                        } catch (e: RealmPrimaryKeyConstraintException) {
                            playlist_name_dialog_edit_text.setError("Name already exists")
                        }

                    }
                }


    }


}