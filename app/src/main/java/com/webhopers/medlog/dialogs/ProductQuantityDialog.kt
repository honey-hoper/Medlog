package com.webhopers.medlog.dialogs

import android.app.AlertDialog
import android.content.Context
import com.webhopers.medlog.R
import com.webhopers.medlog.extensions.inflateView
import kotlinx.android.synthetic.main.product_quantity_dialog.*

class ProductQuantityDialog (context: Context, name: String, quantity: Int) {
    init {
        AlertDialog.Builder(context)
                .setView(context.inflateView(R.layout.product_quantity_dialog))
                .create()
                .apply {
                    show()
                    dialog_product_name.text = name
                    dialog_total_quantity.text = "/${quantity}"
                    dialog_add_to_cart_btn.setOnClickListener {
                        val input = dialog_quantity_input.text.toString().trim()
                        if (input.isBlank()) {
                            dialog_quantity_input.error = "Empty"
                            return@setOnClickListener
                        }
                        val orderedQuantity = input.toInt()
                        if (orderedQuantity > quantity) {
                            dialog_quantity_input.error = "Not enough stock"
                            return@setOnClickListener
                        }
                        dismiss()
                    }
                }
    }
}