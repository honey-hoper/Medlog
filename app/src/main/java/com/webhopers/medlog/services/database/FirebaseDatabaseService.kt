package com.webhopers.medlog.services.database

import com.google.firebase.database.FirebaseDatabase

class FirebaseDatabaseService {

    companion object {
        val database by lazy { FirebaseDatabase.getInstance() }

        fun addMed(path: String, url: String) {
            database.getReference(path)
                    .push()
                    .setValue(url)
        }
    }
}