package com.webhopers.medlog.adapters

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.webhopers.medlog.R
import com.webhopers.medlog.extensions.inflateView
import com.webhopers.medlog.models.WooCommProduct
import com.webhopers.medlog.services.woocommerce.WooCommService
import com.webhopers.medlog.services.woocommerce.WooCommerceRetrofitClient
import kotlinx.android.synthetic.main.list_products_item_list.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListProductAdapter(val dataset: MutableList<WooCommProduct>): RecyclerView.Adapter<ListProductAdapter.ViewHolder>() {

    var loading = false
    val TOTAL_PRODUCT_COUNT = 113
    val PRODUCT_TO_GET = 20
    var remaining_products = TOTAL_PRODUCT_COUNT - 50


    override fun getItemCount() = dataset.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val name = dataset[position].name
        val price = dataset[position].price
        holder.bindData(name, price, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = parent.context.inflateView(R.layout.list_products_item_list)
        return ViewHolder(v)
    }

    fun getMoreProducts(offset: String) {
        val perPage: Int
        if (remaining_products >= PRODUCT_TO_GET) {
            perPage = PRODUCT_TO_GET
            remaining_products -= PRODUCT_TO_GET
        } else {
            perPage = remaining_products
            remaining_products = 0
        }

        val call = WooCommerceRetrofitClient.retrofit.create(WooCommService::class.java)
                 .getProducts(offset = offset, perPage = perPage.toString())
        call.enqueue(object : Callback<List<WooCommProduct>>{
            override fun onFailure(call: Call<List<WooCommProduct>>?, t: Throwable?) {
                loading = false
            }

            override fun onResponse(call: Call<List<WooCommProduct>>, response: Response<List<WooCommProduct>>) {
                loading = false
                val newList = response.body()
                if (newList == null || newList.isEmpty())
                    return
                dataset.addAll(newList)
                notifyItemRangeInserted(itemCount - 1, newList.size)
            }
        })
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        fun bindData(name: String, price: String, position: Int) {
            if (name == "")
                itemView.product_name.text = "N/A"
            else
                itemView.product_name.text = name
            if (price == "")
                itemView.product_price.text = "N/A"
            else
                itemView.product_price.text = "$price Rs"

            itemView.add_to_cart_button.setOnClickListener { println("Add to Cart $position") }
        }
    }

    class ScrollListener(
            val layoutManager: LinearLayoutManager,
            val adapter: ListProductAdapter): RecyclerView.OnScrollListener() {

        var offset = 50
        val visibleThreshold = 10
        var lastVisibleItem = 0
        var visibleItemCount = 0
        var totalItemCount = 0

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            visibleItemCount = recyclerView.childCount
            totalItemCount = layoutManager.itemCount
            lastVisibleItem = layoutManager.findLastVisibleItemPosition()

            if (!adapter.loading && totalItemCount <= (lastVisibleItem + visibleThreshold) && adapter.remaining_products != 0) {
                adapter.getMoreProducts(offset.toString())
                adapter.loading = true
                offset += adapter.PRODUCT_TO_GET

            }
        }
    }


}