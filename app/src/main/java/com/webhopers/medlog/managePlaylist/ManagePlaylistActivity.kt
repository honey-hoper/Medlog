package com.webhopers.medlog.managePlaylist

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem


import com.webhopers.medlog.R
import com.webhopers.medlog.adapters.ListAdapter
import com.webhopers.medlog.services.database.RealmDatabaseService
import kotlinx.android.synthetic.main.activity_manage_playlist.*

class ManagePlaylistActivity :

        ManagePlaylistView,

        AppCompatActivity() {

    lateinit var presenter: ManagePlaylistPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_playlist)

        presenter = ManagePlaylistPresenter(this)
        setSupportActionBar(toolbar_manage_playlist)

        manage_playlist_recycler_view.adapter = ListAdapter(presenter.getPlaylists())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.manage_activity_main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        when (id) {
            R.id.delete_all_playlist -> presenter.deleteAllPlaylists()
        }

        return super.onOptionsItemSelected(item)
    }

    // View Functions
    override fun getRecyclerView() = manage_playlist_recycler_view
}
