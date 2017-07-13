package com.webhopers.medlog.managePlaylist

import com.webhopers.medlog.services.database.RealmDatabaseService

class ManagePlaylistPresenter {

    fun getPlaylists(): List<String?> {
        return RealmDatabaseService.showAllPlaylists()
                .map { it.name }

    }
}