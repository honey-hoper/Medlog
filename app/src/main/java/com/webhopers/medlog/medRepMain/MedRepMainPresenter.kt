package com.webhopers.medlog.medRepMain

import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Window
import com.facebook.drawee.backends.pipeline.Fresco
import com.google.firebase.database.FirebaseDatabase
import com.stfalcon.frescoimageviewer.ImageViewer
import com.webhopers.medlog.R
import com.webhopers.medlog.adapters.RecyclerViewAdapterMR
import com.webhopers.medlog.dataHolder.DataHolder
import com.webhopers.medlog.services.auth.FirebaseAuthService
import com.webhopers.medlog.services.database.RealmDatabaseService
import com.webhopers.medlog.utils.convertDpToPixels
import kotlinx.android.synthetic.main.playlist_picker_dialog.*


class MedRepMainPresenter(val view: MedRepMainView,
                          val context: Context,
                          val activity: AppCompatActivity,
                          val resources: Resources) {

    init {
        DataHolder.changeView(view)
    }


    fun signout() {
        view.showProgressDialog(true)
        FirebaseAuthService.signOutUser()
        view.showProgressDialog(false)
        view.startSplashActivity()
    }

    fun changeRecyclerViewAdapter(path: String, resources: Resources) {
        val ref = FirebaseDatabase.getInstance()
                .reference
                .child(path)

        view.showProgressBar(true)
        DataHolder.changePath(path)

        val adapter = RecyclerViewAdapterMR(view, context, activity, resources, ref)
        view.getRecyclerView().adapter = adapter
        view.getDecorView().background = ContextCompat.getDrawable(context, R.color.colorAccent)

    }

    fun showPlaylistPicker() {

        val playlists = RealmDatabaseService.showAllPlaylists()
        if (playlists.isEmpty()) {
            view.makeToast("No Playlist")
            return
        }

        var selectedVal = 0

        val arr: Array<String?> = Array(playlists.size) {
            return@Array playlists[it].name
        }

        val dialog = Dialog(activity)
       // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setTitle("Open")
        dialog.setContentView(R.layout.playlist_picker_dialog)

        val picker = dialog.playlist_picker
        picker.minValue = 0
        picker.maxValue = playlists.size - 1
        picker.wrapSelectorWheel = false
        picker.displayedValues = arr
        picker.setOnValueChangedListener { picker, oldVal, newVal -> selectedVal = newVal }

        val selectButton = dialog.select_playlist
        selectButton.setOnClickListener {

            val urls = playlists[selectedVal].urls?.map { it.url }

            ImageViewer.Builder(context, urls)
                    .setStartPosition(0)
                    .setImageMarginPx(convertDpToPixels(25f, resources).toInt())
                    .show()

            Fresco.initialize(context)

            dialog.dismiss()
        }
        dialog.show()
    }


}
