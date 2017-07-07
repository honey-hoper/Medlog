package com.webhopers.medlog.services.storage

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.webhopers.medlog.services.database.FirebaseDatabaseService
import io.reactivex.Observable


class FirebaseStorageService {

    companion object {
        val storage by lazy { FirebaseStorage.getInstance()}

        fun uploadFile(file: Uri, storagePath: String): Observable<Int> {
            return Observable.create { e ->
                val storageRef = storage.reference.child("$storagePath/${file.lastPathSegment}")
                storageRef.putFile(file)
                        .addOnSuccessListener { taskSnapshot ->
                            FirebaseDatabaseService.addMed(storagePath, taskSnapshot.downloadUrl.toString())
                            e.onComplete()
                        }
                        .addOnFailureListener { exception ->  e.onError(Throwable("Unable to Upload File ${exception.message}")) }
                        .addOnProgressListener { taskSnapshot ->
                            val progressPercent = taskSnapshot.bytesTransferred * 100 / taskSnapshot.totalByteCount
                            e.onNext(progressPercent.toInt())
                        }
            }
        }

        fun deleteFile(url: String) {
            storage.getReferenceFromUrl(url).delete()
        }
    }


}