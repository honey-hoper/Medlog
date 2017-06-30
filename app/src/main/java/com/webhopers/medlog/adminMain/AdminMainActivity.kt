package com.webhopers.medlog.adminMain

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.GridLayoutManager
import android.view.Menu
import android.view.MenuItem

import com.webhopers.medlog.R
import com.webhopers.medlog.adminMain.recyclerView.RecyclerViewAdapterAdmin
import com.webhopers.medlog.medRepMain.navigationDrawer.ExpandableListAdapter
import com.webhopers.medlog.medRepMain.recyclerView.RecyclerViewAdapterMR
import com.webhopers.medlog.medRepMain.recyclerView.RecyclerViewDecorator
import com.webhopers.medlog.utils.convertDpToPixels
import kotlinx.android.synthetic.main.activity_admin_main.*
import kotlinx.android.synthetic.main.activity_med_rep_main.*

class AdminMainActivity : AppCompatActivity() {

    lateinit var drawerToggle: ActionBarDrawerToggle
    lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_main)

        drawerToggle = ActionBarDrawerToggle(this, drawer_admin, R.string.open_drawer, R.string.close_drawer)
        initUI()

        recycler_view_admin.layoutManager = GridLayoutManager(this, 2)
        recycler_view_admin.adapter = RecyclerViewAdapterAdmin(this)
        recycler_view_admin.addItemDecoration(RecyclerViewDecorator(2,
                convertDpToPixels(1f, resources).toInt(),
                true))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.admin_main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (drawerToggle.onOptionsItemSelected(item)) return true
        return super.onOptionsItemSelected(item)
    }

    //UI element creation

    private fun initUI() {
        addToolbar()
        initNavigationDrawer()
        addActionBarDrawerToggle()
        createProgressDialog()
    }

    private fun addToolbar() {
        setSupportActionBar(toolbar_admin)
    }

    private fun initNavigationDrawer() {
        val header = arrayListOf<String>(getString(R.string.group_product_type), getString(R.string.group_product_for))
        val grp1 = resources.getStringArray(R.array.product_types).asList()
        val grp2 = resources.getStringArray(R.array.product_for).asList()

        val adapter = ExpandableListAdapter(this, header, hashMapOf(header[0] to grp1, header[1] to grp2))
        exp_list_view_admin.setAdapter(adapter)
        exp_list_view_admin.setOnGroupExpandListener {
            if (it == 0) exp_list_view_admin.collapseGroup(1) else exp_list_view_admin.collapseGroup(0)
        }
        drawer_admin.addDrawerListener(drawerToggle)
    }

    private fun addActionBarDrawerToggle() {
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        drawerToggle.syncState()

    }

    private fun createProgressDialog() {
        progressDialog = ProgressDialog(this, ProgressDialog.STYLE_SPINNER)
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.isIndeterminate = true
        progressDialog.setMessage("wait")
    }
}
