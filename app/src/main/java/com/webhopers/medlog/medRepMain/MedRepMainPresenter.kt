package com.webhopers.medlog.medRepMain

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.webhopers.medlog.adapters.RecyclerViewAdapterMR
import com.webhopers.medlog.dataHolder.DataHolder
import com.webhopers.medlog.services.auth.FirebaseAuthService
import java.io.File
import java.io.FileFilter


class MedRepMainPresenter(val view: MedRepMainView,
                          val context: Context,
                          val activity: AppCompatActivity) {

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

    }

    fun startSavedImagesIntent() {
        val file = File("${Environment
                .getExternalStorageDirectory()
                .absolutePath}/medlog/")
        val files = file.listFiles(FileFilter { pathname -> return@FileFilter pathname.name.contains(".jpg") })

        val intent = Intent()
        intent.setAction(Intent.ACTION_VIEW)
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.setDataAndType(Uri.fromFile(files[0]), "image/*")
        view.startIntentActivity(intent)

    }


}
