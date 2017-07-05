package com.webhopers.medlog.services.database

import com.google.firebase.database.FirebaseDatabase
import com.webhopers.medlog.models.MedRepInfo

class FirebaseDatabaseService {

    companion object {
        val database by lazy { FirebaseDatabase.getInstance() }

        val users = "users"

        fun addMed(path: String, url: String) {
            database.getReference(path)
                    .push()
                    .setValue(url)
        }

        fun addUser(mr: MedRepInfo) {
            database.getReference(users)
                    .child(mr.uid)
                    .setValue(mr)
        }
    }
}