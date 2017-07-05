package com.webhopers.medlog.medRepMain

import android.content.Context
import android.content.res.Resources
import com.google.firebase.database.FirebaseDatabase
import com.webhopers.medlog.adapters.RecyclerViewAdapterMR
import com.webhopers.medlog.services.auth.FirebaseAuthService

class MedRepMainPresenter(val view: MedRepMainView, val context: Context) {

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

        val adapter = RecyclerViewAdapterMR(context, resources, ref)
        view.getRecyclerView().adapter = adapter
    }
}
