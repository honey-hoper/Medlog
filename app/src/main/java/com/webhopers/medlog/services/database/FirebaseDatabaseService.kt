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

        fun getAllFromPath(path: String): Single<ArrayList<String?>> {
            return Single.create<ArrayList<String?>> { e ->
                database.getReference(path)
                        .addListenerForSingleValueEvent(object: ValueEventListener {
                            override fun onCancelled(error: DatabaseError?) {
                                e.onError(Throwable("Error: ${error?.message}" ))
                            }

                            override fun onDataChange(snapshot: DataSnapshot?) {
                                val list = ArrayList<String?>()
                                for (x in snapshot!!.children)
                                    list.add(x.getValue(ImageModel::class.java)?.url)
                                e.onSuccess(list)
                            }
                        })
            }
        }

        fun getAllFromPath2(path: String): Single<ArrayList<String>> {
            return Single.create<ArrayList<String>> { e ->
                database.getReference(path)
                        .addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onCancelled(error: DatabaseError?) {
                                e.onError(Throwable("Error: ${error?.message}" ))
                            }

                            override fun onDataChange(snapshot: DataSnapshot?) {
                                val list: ArrayList<String> = ArrayList<String>()
                                for (x in snapshot!!.children)
                                    list.add(x.getValue(ImageModel::class.java)!!.url!!)
                                e.onSuccess(list)
                            }

                        })
            }
        }
    }
}