package com.webhopers.medlog.medRepMain

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import com.webhopers.medlog.medRepMain.navigationDrawer.ExpandableListAdapter

import com.webhopers.medlog.R
import kotlinx.android.synthetic.main.activity_med_rep_main.*

class MedRepMainActivity: AppCompatActivity() {


    var drawerToggle: ActionBarDrawerToggle? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_med_rep_main)
        drawerToggle = ActionBarDrawerToggle(this, drawer, R.string.open_drawer, R.string.close_drawer)
        initUI()


        // mr_recycler_view.layoutManager = GridLayoutManager(this, 2)
        // mr_recycler_view.adapter = RecyclerViewAdapter(this)

    }

    private fun initUI() {
        addToolbar()
        initNavigationDrawer()
        addActionBarDrawerToggle()
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
        drawer.addDrawerListener(drawerToggle!!)
    }

    private fun addActionBarDrawerToggle() {
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        drawerToggle?.syncState()

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (drawerToggle!!.onOptionsItemSelected(item)) return true

        return super.onOptionsItemSelected(item)
    }
}