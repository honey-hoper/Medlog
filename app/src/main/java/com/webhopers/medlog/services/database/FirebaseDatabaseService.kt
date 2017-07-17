package com.webhopers.medlog.services.database

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.webhopers.medlog.models.ImageModel
import com.webhopers.medlog.models.MedRepInfo
import io.reactivex.Single

class FirebaseDatabaseService {

    companion object {
        val database by lazy { FirebaseDatabase.getInstance() }

        val users = "users"
        val images = "images"

        fun createMed(path: String, url: String) {
            val uid = database.getReference(images)
                    .push()
                    .key

            database.getReference(images)
                    .child(uid)
                    .push()
                    .setValue(path)

            addMed2(uid, url, path)

        }

        fun addMed2(uid: String, url: String, path: String) {
            val imageModel = ImageModel(uid, url)
            database.getReference(path)
                    .child(uid)
                    .setValue(imageModel)

            database.getReference(images)
                    .child(uid)
                    .push()
                    .setValue(path)
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