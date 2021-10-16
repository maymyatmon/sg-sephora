package com.may.sephora.view

import android.text.Spanned
import androidx.core.text.HtmlCompat
import com.may.sephora.model.Included
import com.may.sephora.model.Product

object ProductHelper {

    fun getBrand(product: Product, includedList: List<Included>): String {
        val list =
            includedList.filter { it.type == product.relationships.brand.data.type && it.id == product.relationships.brand.data.id }
        val included = list[0]
        return included.attributes.name
    }

    fun getPercentage(originalPrice: Double, price: Double): Int {
        return ((originalPrice - price) / originalPrice * 100).toInt()
    }

    fun getFormattedPrice(price: Double): String {
        return String.format("\$%.2f", price)
    }

    fun getSpannedText(text: String): Spanned {
        return HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_COMPACT)
    }
}