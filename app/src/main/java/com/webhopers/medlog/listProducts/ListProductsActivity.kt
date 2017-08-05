package com.webhopers.medlog.listProducts

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.ActionMode
import android.view.View
import android.widget.Button
import android.widget.Toast

import com.webhopers.medlog.R
import com.webhopers.medlog.adapters.ListProductAdapter
import com.webhopers.medlog.models.WooCommProduct
import com.webhopers.medlog.services.woocommerce.WooCommService
import com.webhopers.medlog.services.woocommerce.WooCommerceRetrofitClient
import kotlinx.android.synthetic.main.activity_list_products.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListProductsActivity : ListProductsView, AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_products)

        val presenter = ListProductsPresenter(this)

        showProgressBar(true)
    }

    // ListProduct View functions

    override fun showProgressBar(bool: Boolean) {
        if (bool) list_products_p_bar.visibility = View.VISIBLE
        else list_products_p_bar.visibility = View.INVISIBLE
    }

    override fun makeToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    override fun getRecyclerView() = list_products_rec_view

}
