package com.webhopers.medlog.medRepMain

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.GridLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.webhopers.medlog.medRepMain.navigationDrawer.ExpandableListAdapter

import com.webhopers.medlog.R
import com.webhopers.medlog.medRepMain.recyclerView.RecyclerViewAdapter
import com.webhopers.medlog.medRepMain.recyclerView.RecyclerViewDecorator
import com.webhopers.medlog.splash.SplashActivity
import com.webhopers.medlog.utils.convertDpToPixels
import kotlinx.android.synthetic.main.activity_med_rep_main.*

class MedRepMainActivity: MedRepMainView, AppCompatActivity() {

    lateinit var drawerToggle: ActionBarDrawerToggle
    lateinit var progressDialog: ProgressDialog

    lateinit var presenter: MedRepMainPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_med_rep_main)


        presenter = MedRepMainPresenter(this, this)
        drawerToggle = ActionBarDrawerToggle(this, drawer, R.string.open_drawer, R.string.close_drawer)
        initUI()


         mr_recycler_view.layoutManager = GridLayoutManager(this, 2)
         mr_recycler_view.adapter = RecyclerViewAdapter(this)
        mr_recycler_view.addItemDecoration(RecyclerViewDecorator(2,
                convertDpToPixels(1f, resources).toInt(),
                true))

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.med_rep_main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (drawerToggle.onOptionsItemSelected(item)) return true
        val id = item?.itemId

        when (id) {
             R.id.signout_option -> presenter.signout()
        }

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
        setSupportActionBar(toolbar)
    }

    private fun initNavigationDrawer() {
        val header = arrayListOf<String>(getString(R.string.group_product_type), getString(R.string.group_product_for))
        val grp1 = resources.getStringArray(R.array.product_types).asList()
        val grp2 = resources.getStringArray(R.array.product_for).asList()

        val adapter = ExpandableListAdapter(this, header, hashMapOf(header[0] to grp1, header[1] to grp2))
        exp_list_view.setAdapter(adapter)
        exp_list_view.setOnGroupExpandListener {
            if (it == 0) exp_list_view.collapseGroup(1) else exp_list_view.collapseGroup(0)
        }
        drawer.addDrawerListener(drawerToggle)
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

    // MedRepMain View Functions

    override fun startSplashActivity() {
        startActivity(Intent(this, SplashActivity::class.java))
        finish()
    }

    override fun showProgressDialog(bool: Boolean) {
        if (bool) progressDialog.show() else progressDialog.hide()
    }



}