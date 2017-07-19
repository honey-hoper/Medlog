package com.webhopers.medlog.medRepMain

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.GridLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.webhopers.medlog.adapters.ExpandableListAdapter

import com.webhopers.medlog.R
import com.webhopers.medlog.adapters.itemDecorator.RecyclerViewDecorator
import com.webhopers.medlog.managePlaylist.ManagePlaylistActivity
import com.webhopers.medlog.services.database.RealmDatabaseService
import com.webhopers.medlog.splash.SplashActivity
import com.webhopers.medlog.utils.convertDpToPixels
import kotlinx.android.synthetic.main.activity_med_rep_main.*
import kotlinx.android.synthetic.main.exp_list_child_item_mr.view.*

class MedRepMainActivity: MedRepMainView, AppCompatActivity() {


    lateinit var drawerToggle: ActionBarDrawerToggle
    lateinit var progressDialog: ProgressDialog

    lateinit var presenter: MedRepMainPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_med_rep_main)

        presenter = MedRepMainPresenter(this, this, this, resources)

        drawerToggle = ActionBarDrawerToggle(this, drawer_mr, R.string.open_drawer, R.string.close_drawer)

        exp_list_view_mr.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
            presenter.changeRecyclerViewAdapter(v.list_item.text.toString(), resources)
            drawer_mr.closeDrawers()
            return@setOnChildClickListener  false
        }

        initUI()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.med_rep_main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (drawerToggle.onOptionsItemSelected(item)) return true
        val id = item?.itemId

        when (id) {
            R.id.signout_option_mr -> presenter.signout()
            R.id.saved_option_mr -> presenter.showPlaylistPicker()
            R.id.manage_playlists -> startManagePlaylistActivity()
        }

        return super.onOptionsItemSelected(item)
    }

    //UI element creation

    private fun initUI() {
        addToolbar()
        initNavigationDrawer()
        addActionBarDrawerToggle()
        initRecyclerView()
        createProgressDialog()
    }

    private fun addToolbar() {
        setSupportActionBar(toolbar_mr)
    }

    private fun initNavigationDrawer() {
        val header = arrayListOf<String>(getString(R.string.group_product_type), getString(R.string.group_product_for))
        val grp1 = resources.getStringArray(R.array.product_types).asList()
        val grp2 = resources.getStringArray(R.array.product_for).asList()

        val adapter = ExpandableListAdapter(this, header, hashMapOf(header[0] to grp1, header[1] to grp2))
        exp_list_view_mr.setAdapter(adapter)
        exp_list_view_mr.setOnGroupExpandListener {
            if (it == 0) exp_list_view_mr.collapseGroup(1) else exp_list_view_mr.collapseGroup(0)
        }
        drawer_mr.addDrawerListener(drawerToggle)
    }

    private fun addActionBarDrawerToggle() {
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        drawerToggle.syncState()

    }

    private fun initRecyclerView() {
        recycler_view_mr.layoutManager = GridLayoutManager(this, 2)
        recycler_view_mr.addItemDecoration(RecyclerViewDecorator(2,
                convertDpToPixels(1f, resources).toInt(),
                true))
    }

    private fun createProgressDialog() {
        progressDialog = ProgressDialog(this, ProgressDialog.STYLE_SPINNER)
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.isIndeterminate = true
        progressDialog.setMessage("wait")
    }


    // MedRepMain View Functions

    override fun startSplashActivity() {
        startActivity(Intent(this, SplashActivity::class.java))
        finish()
    }

    override fun showProgressDialog(bool: Boolean) {
        if (bool) progressDialog.show() else progressDialog.hide()
    }

    override fun getRecyclerView() = recycler_view_mr

    override fun showProgressBar(bool: Boolean) {
        progress_bar_mr.visibility = if (bool) View.VISIBLE else View.GONE
    }

    override fun startIntentActivity(intent: Intent) = startActivity(intent)

    override fun makeToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    override fun startManagePlaylistActivity() = startActivity(Intent(this, ManagePlaylistActivity::class.java))

    override fun getRootLayout() = med_rep_root

    override fun getContext() = this

}