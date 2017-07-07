package com.webhopers.medlog.viewImage

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.webhopers.medlog.R

class ViewImageActivity : AppCompatActivity() {

    lateinit var presenter: ViewImagePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_view_image)

        presenter = ViewImagePresenter(this, resources)
        presenter.getAllFromPath("Tablet")
    }
}
