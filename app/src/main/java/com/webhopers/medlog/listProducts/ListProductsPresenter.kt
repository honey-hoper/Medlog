package com.webhopers.medlog.listProducts

import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.webhopers.medlog.R
import com.webhopers.medlog.adapters.ListProductAdapter
import com.webhopers.medlog.models.WooCommProduct
import com.webhopers.medlog.services.woocommerce.WooCommService
import com.webhopers.medlog.services.woocommerce.WooCommerceRetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListProductsPresenter(val view: ListProductsView) {

    lateinit var products: List<WooCommProduct>

    init {
        WooCommerceRetrofitClient.retrofit.create(WooCommService::class.java)
                .getProducts()
                .enqueue(object : Callback<List<WooCommProduct>> {
                    override fun onResponse(call: Call<List<WooCommProduct>>, response: Response<List<WooCommProduct>>) {
                        view.showProgressBar(false)
                        if (response.isSuccessful) products = response.body() ?: ArrayList<WooCommProduct>()
                        else view.makeToast("Unable to fetch products")
                        setAdapter()
                    }

                    override fun onFailure(call: Call<List<WooCommProduct>>, t: Throwable) {
                        view.showProgressBar(false)
                        view.makeToast("Unable to fetch products")
                    }
                })

        WooCommerceRetrofitClient.retrofit.create(WooCommService::class.java)
                .getProductCount()
                .enqueue(object : Callback<Int> {
                    override fun onResponse(call: Call<Int>, response: Response<Int>) {
                        if (response.isSuccessful) println("--------------Success ${response.body()}")
                        else println("---------------failed ${response.code()}")
                    }

                    override fun onFailure(call: Call<Int>, t: Throwable) {
                        println(t.message)
                    }
                })
    }

    private fun setAdapter() {
        val adapter = ListProductAdapter(products.toMutableList())
        val itemDecoration = DividerItemDecoration(view.getRecyclerView().context, LinearLayoutManager.VERTICAL)
        itemDecoration.setDrawable(ContextCompat.getDrawable(view.getRecyclerView().context, R.drawable.divider))

        view.getRecyclerView().apply {
            this.adapter = adapter
            val lm = LinearLayoutManager(context)
            layoutManager = lm
            setHasFixedSize(true)
            addOnScrollListener(ListProductAdapter.ScrollListener(lm, adapter))
            addItemDecoration(itemDecoration)
        }

        }
}

