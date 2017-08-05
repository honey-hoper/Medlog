package com.webhopers.medlog.services.woocommerce

import com.webhopers.medlog.models.WooCommProduct
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WooCommService {

    @GET("products")
    fun getProducts(
            @Query("per_page") perPage: String = "50",
            @Query("offset") offset: String = "0",
            @Query("order") order: String = "asc",
            @Query("orderby") orderBy: String = "title"): Call<List<WooCommProduct>>

    @GET("products/count")
    fun getProductCount(): Call<Int>
}