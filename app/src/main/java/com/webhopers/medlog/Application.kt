package com.webhopers.medlog

import android.app.Application
import io.realm.Realm

class Application: Application() {

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)
    }
}