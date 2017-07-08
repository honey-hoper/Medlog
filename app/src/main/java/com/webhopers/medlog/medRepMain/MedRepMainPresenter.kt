package com.webhopers.medlog.medRepMain

import android.content.Context
import android.content.res.Resources
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.webhopers.medlog.adapters.RecyclerViewAdapterMR
import com.webhopers.medlog.services.auth.FirebaseAuthService
import com.webhopers.medlog.services.database.FirebaseDatabaseService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class MedRepMainPresenter(val view: MedRepMainView,
                          val context: Context,
                          val disposable: CompositeDisposable = CompositeDisposable()) {


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
        getAllFromPath(context, path, resources, ref)
    }

    fun getAllFromPath(context: Context, path: String, resources: Resources, ref: DatabaseReference) {
        disposable.add(FirebaseDatabaseService.getAllFromPath2(path)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onError = {
                            view.showProgressBar(false)
                        },
                        onSuccess = {arrayList ->
                            view.showProgressBar(false)
                           if (arrayList != null)  {
                               val adapter = RecyclerViewAdapterMR(context, resources, arrayList, ref)
                               view.getRecyclerView().adapter = adapter
                           }
                        }
                ))
    }

    fun unsubscribe() {
        disposable.clear()
    }
}
