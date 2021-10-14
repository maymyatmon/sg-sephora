package com.may.sephora.model

import com.google.gson.annotations.SerializedName

data class Product(
    val type: String,
    val id: Int = 0,
    val attributes: Attribute,
) {
    data class Attribute(
        val name: String,
        val heading: String,
        val rating: String,
        @SerializedName("how-to-text")
        val text: String,
        val description: String,
        val benefits: String,
        val ingredients: String,
        val price: Double,
        @SerializedName("original-price")
        val originalPrice: Double,
        @SerializedName("under-sale")
        val isSale: Boolean,
        @SerializedName("sale-text")
        val saleDescription: Boolean,
        @SerializedName("sold-out")
        val isSoldOut: Boolean,
        )
}