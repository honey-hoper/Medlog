package com.webhopers.medlog.services.database

import com.google.firebase.database.FirebaseDatabase
import com.webhopers.medlog.models.ImageModel
import com.webhopers.medlog.models.MedRepInfo

class FirebaseDatabaseService {

    companion object {
        val database by lazy { FirebaseDatabase.getInstance() }

        val users = "users"

        fun addMed(path: String, url: String) {
            val uid = database.getReference(path)
                    .push()
                    .key

            val imageModel = ImageModel(uid, url)
            database.getReference(path)
                    .child(uid)
                    .setValue(imageModel)

        }

        fun removeMed(uid: String, path: String) {
            database.getReference(path)
                    .child(uid)
                    .removeValue()
        }

        fun addUser(mr: MedRepInfo) {
            database.getReference(users)
                    .child(mr.uid)
                    .setValue(mr)
        }
    }
}