package com.webhopers.medlog.services.database

import com.webhopers.medlog.models.Playlist
import io.realm.Realm

object RealmDatabaseService {

    val realm by lazy { Realm.getDefaultInstance() }

    fun createPlaylist(playlist: Playlist) {
        realm.executeTransaction { realm ->
            realm.copyToRealm(playlist)
        }
    }

    fun showResult() {
        realm.where(Playlist::class.java)
                .findAll()
                .forEach {
                    println(it.name)
                    it.urls?.forEach {
                        println(it.url)
                    }
                }
    }

    fun showAllPlaylists(): List<Playlist> {
        val result = realm.where(Playlist::class.java)
                        .findAll()
        return result
    }


    fun deleteAll() {

        realm.executeTransaction { realm ->
            realm.deleteAll()
        }
    }


}