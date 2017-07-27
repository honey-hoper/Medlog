package com.webhopers.medlog.services.database

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.webhopers.medlog.models.ImageModel
import com.webhopers.medlog.models.MedRepInfo
import io.reactivex.Observable
import io.reactivex.Single

class FirebaseDatabaseService {

    companion object {
        val database by lazy { FirebaseDatabase.getInstance() }

        val users = "users"
        val images = "images"
        val ACC = "ACC"

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
            val list = arrayListOf<String>()
            database.getReference(images)
                    .child(uid)
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(p0: DataSnapshot?) {
                            for (x in p0!!.children) {
                                list.add(x.getValue(String::class.java).toString())
                            }

                            list.forEach {
                                database.getReference(it)
                                        .child(uid)
                                        .removeValue()
                            }

                            database.getReference(images)
                                    .child(uid)
                                    .removeValue()

                        }

                        override fun onCancelled(p0: DatabaseError?) {}
                    })

        }

        fun addUser(mr: MedRepInfo) {
            database.getReference(users)
                    .child(mr.uid)
                    .setValue(mr)
        }

        fun resetACC(code: Int) {
            database.getReference(ACC)
                    .setValue(code)
        }

        fun getACC(): Observable<Int> {
            return Observable.create<Int> { e ->
                database.getReference(ACC)
                        .addValueEventListener(object : ValueEventListener {
                            override fun onCancelled(p0: DatabaseError?) {
                                e.onError(Throwable("Cancelled"))
                                e.onComplete()
                            }

                            override fun onDataChange(p0: DataSnapshot?) {
                                e.onNext(p0?.getValue(Int::class.java)!!)
                            }

                        })
            }

        }

        fun removeUser(uid: String) {
            database.getReference(users)
                    .child(uid)
                    .removeValue()
        }


    }
}