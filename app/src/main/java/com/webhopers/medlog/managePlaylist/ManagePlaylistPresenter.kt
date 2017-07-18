package com.webhopers.medlog.managePlaylist

import com.webhopers.medlog.services.database.RealmDatabaseService

class ManagePlaylistPresenter(val view: ManagePlaylistView) {


    fun getPlaylists(): MutableList<String?> {
        return (RealmDatabaseService.showAllPlaylists()
                .map { it.name }).toMutableList()

    }

    fun deleteAllPlaylists() {
        view.getRecyclerView().adapter = null
        RealmDatabaseService.deleteAll()
    }
}