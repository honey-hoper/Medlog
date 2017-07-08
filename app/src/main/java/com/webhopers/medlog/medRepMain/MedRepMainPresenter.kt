package com.webhopers.medlog.medRepMain

import android.content.Context
import android.content.res.Resources
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.webhopers.medlog.adapters.RecyclerViewAdapterMR
import com.webhopers.medlog.dataHolder.DataHolder
import com.webhopers.medlog.services.auth.FirebaseAuthService
import com.webhopers.medlog.services.database.FirebaseDatabaseService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class MedRepMainPresenter(val view: MedRepMainView,
                          val context: Context) {

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

        val adapter = RecyclerViewAdapterMR(view, context, resources, ref)
        view.getRecyclerView().adapter = adapter

    }


}
