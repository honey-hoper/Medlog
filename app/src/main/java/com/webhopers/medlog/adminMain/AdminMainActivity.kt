package com.webhopers.medlog.adminMain

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.GridLayoutManager
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView

import com.webhopers.medlog.R
import com.webhopers.medlog.adapters.ExpandableListAdapter
import com.webhopers.medlog.adapters.itemDecorator.RecyclerViewDecorator
import com.webhopers.medlog.splash.SplashActivity
import com.webhopers.medlog.utils.convertDpToPixels
import kotlinx.android.synthetic.main.activity_admin_main.*
import kotlinx.android.synthetic.main.exp_list_child_item_mr.view.*

class AdminMainActivity : AdminMainView, AppCompatActivity() {

    lateinit var drawerToggle: ActionBarDrawerToggle
    lateinit var progressDialog: ProgressDialog

    lateinit var presenter: AdminMainPresenter

    lateinit var path: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_main)

        presenter = AdminMainPresenter(this, this, this, resources)

        drawerToggle = ActionBarDrawerToggle(this, drawer_admin, R.string.open_drawer, R.string.close_drawer)

        exp_list_view_admin.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
            path = v.list_item.text.toString()
            presenter.changeRecyclerViewAdapter(path, resources)
            drawer_admin.closeDrawers()
            upload_button.visibility = View.VISIBLE
            return@setOnChildClickListener  false
        }

        upload_button.setOnClickListener { startCropImageActivity() }

        initUI()

    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (drawerToggle.onOptionsItemSelected(item)) return true
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unsubscribe()
    }

    fun startCropImageActivity() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK && result.uri != null) {
                presenter.upload(result.uri, path)
            }
        }
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

    private fun initRecyclerView() {
        recycler_view_admin.layoutManager = GridLayoutManager(this, 2)
        recycler_view_admin.addItemDecoration(RecyclerViewDecorator(2,
                convertDpToPixels(1f, resources).toInt(),
                true))
    }

    private fun createProgressDialog() {
        progressDialog = ProgressDialog(this)
    }

    // MedRepMain View Functions

    override fun startSplashActivity() {
        startActivity(Intent(this, SplashActivity::class.java))
        finish()
    }

    override fun showProgressDialog(bool: Boolean) {
        if (bool) progressDialog.show() else progressDialog.hide()
    }

    override fun setProgressDialogStyle(style: Int, message: String, indeterminate: Boolean) {
        progressDialog.setProgressStyle(style)
        progressDialog.setMessage(message)
        progressDialog.isIndeterminate = indeterminate
    }

    override fun setProgressDialogProgress(progress: Int) {
        progressDialog.progress = progress
    }

    override fun getRecyclerView() = recycler_view_admin

    override fun makeToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

}
