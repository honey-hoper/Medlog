package com.webhopers.medlog.services.auth

import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Completable

class FirebaseAuthService {

    companion object {
        val auth by lazy {
            FirebaseAuth.getInstance()
            }

        fun isSessionActive(): Boolean {
            return auth.currentUser != null
        }

        fun createUser(email: String, password: String): Completable {
            return Completable.create { e -> auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener {
                            if (it.isSuccessful) e.onComplete()
                            else e.onError(Throwable("Unable to create account ${it.exception}"))
                        }}

        }

        fun signInUser(email: String, password: String): Completable {
            return Completable.create {  e ->  auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) e.onComplete()
                        else e.onError(Throwable("Unable to sign in ${it.exception}"))
                    }}
        }

        fun signOutUser() {
            auth.signOut()
        }
    }


}