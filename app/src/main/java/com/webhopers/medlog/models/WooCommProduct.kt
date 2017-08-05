package com.webhopers.medlog.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WooCommProduct(
        @SerializedName("name")
        @Expose
        val name: String,
        @SerializedName("price")
        @Expose
        val price: String,
        @SerializedName("stock_quantity")
        @Expose
        val stockQuantity: Int

) {
}