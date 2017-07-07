package com.webhopers.medlog.viewImage

import android.content.Context
import android.content.res.Resources
import com.facebook.drawee.backends.pipeline.Fresco
import com.stfalcon.frescoimageviewer.ImageViewer
import com.webhopers.medlog.services.database.FirebaseDatabaseService
import com.webhopers.medlog.utils.convertDpToPixels
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class ViewImagePresenter(val context: Context,
                         val resources: Resources,
                         val disposable: CompositeDisposable = CompositeDisposable()) {

    fun getAllFromPath(path: String) {
        disposable.add(FirebaseDatabaseService.getAllFromPath(path)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onError = {},
                        onSuccess = {arrayList ->
                            startFrescoImageViewActivty(context, arrayList) }
                        ))
    }

    fun startFrescoImageViewActivty(context: Context, urls: ArrayList<String?>, position: Int = 0) {
        ImageViewer.Builder(context, urls)
                .setStartPosition(position)
                .setImageMarginPx(convertDpToPixels(25f, resources).toInt())
                .show()
        Fresco.initialize(context)
    }
}