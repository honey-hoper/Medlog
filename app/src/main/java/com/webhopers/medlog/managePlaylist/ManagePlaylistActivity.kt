package com.webhopers.medlog.managePlaylist

import android.support.v7.app.AppCompatActivity
import android.os.Bundle


import com.webhopers.medlog.R
import com.webhopers.medlog.adapters.ListAdapter
import kotlinx.android.synthetic.main.activity_manage_playlist.*

class ManagePlaylistActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_playlist)

        val presenter = ManagePlaylistPresenter()

        manage_playlist_recycler_view.adapter = ListAdapter(presenter.getPlaylists())
    }
}
