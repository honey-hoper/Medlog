package com.webhopers.medlog.MedRepMain

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.webhopers.medlog.MedRepMain.navigationDrawer.ExpandableListAdapter

import com.webhopers.medlog.R
import kotlinx.android.synthetic.main.activity_med_rep_main.*

class MedRepMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_med_rep_main)
        createNavigationDrawer()

    }

    private fun createNavigationDrawer() {
        val header = arrayListOf<String>(getString(R.string.group_product_type), getString(R.string.group_product_for))
        val grp1 = resources.getStringArray(R.array.product_types).asList()
        val grp2 = resources.getStringArray(R.array.product_for).asList()

        val adapter = ExpandableListAdapter(this, header, hashMapOf(header[0] to grp1, header[1] to grp2))
        exp_list_view.setAdapter(adapter)
        exp_list_view.setOnGroupExpandListener {
            if (it == 0) exp_list_view.collapseGroup(1) else exp_list_view.collapseGroup(0)
        }
    }
}
