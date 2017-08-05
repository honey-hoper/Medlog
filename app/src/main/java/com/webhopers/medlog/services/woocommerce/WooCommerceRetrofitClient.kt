package com.webhopers.medlog.services.woocommerce

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object WooCommerceRetrofitClient {
    val INNOVEXIA_BASE_URL = "http://www.innovexia.com/wp-json/wc/v1/"
    val retrofit by lazy {
        val oauth1WooCommerce = OAuthInterceptor()
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .addInterceptor(oauth1WooCommerce)
                .hostnameVerifier { hostname, session -> return@hostnameVerifier true }
                .build()


        Retrofit.Builder()
                .baseUrl(INNOVEXIA_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

    }
}