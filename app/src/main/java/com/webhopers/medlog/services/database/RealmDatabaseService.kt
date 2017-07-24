package com.webhopers.medlog.services.database

import com.webhopers.medlog.models.Image
import com.webhopers.medlog.models.Playlist
import io.realm.Realm
import io.realm.RealmList

object RealmDatabaseService {

    val realm by lazy { Realm.getDefaultInstance() }


    fun createPlaylist(playlist: Playlist) {
        realm.executeTransaction { realm ->
            realm.copyToRealm(playlist)
        }
    }


    fun showAllPlaylists(): List<Playlist> {
        val result = realm.where(Playlist::class.java)
                        .findAll()
        return result
    }

    fun addToPlaylist(playlistName: String, images: List<Image>) {
        val result = realm.where(Playlist::class.java)
                .equalTo("name", playlistName)
                .findAll()

        if (result.isEmpty())
            return

        realm.executeTransaction {
            result[0].urls?.addAll(images)
        }

    }

    fun deleteAll() {

        realm.executeTransaction { realm ->
            realm.deleteAll()
        }
    }

    fun deletePlaylist(playlistName: String) {
        val result = realm.where(Playlist::class.java)
                .equalTo("name", playlistName)
                .findFirst()

        if (result != null) {
            realm.executeTransaction {
                result.deleteFromRealm()
            }
        }
    }


}