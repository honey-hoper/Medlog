package com.webhopers.medlog.managePlaylist

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
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

        setUpToolbar()


        val adapter = ListAdapter(presenter.getPlaylists())
        manage_playlist_recycler_view.adapter = adapter

        val itemDecoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        itemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider))
        manage_playlist_recycler_view.addItemDecoration(itemDecoration)

        val touchHelper = ItemTouchHelper(adapter.itemTouchCallback)
        touchHelper.attachToRecyclerView(manage_playlist_recycler_view)

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

    private fun setUpToolbar() {
        setSupportActionBar(toolbar_manage_playlist)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    // View Functions
    override fun getRecyclerView() = manage_playlist_recycler_view
}
