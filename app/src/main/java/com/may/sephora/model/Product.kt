package com.may.sephora.model

import com.google.gson.annotations.SerializedName

data class Product(
    val type: String,
    val id: Int = 0,
    val attributes: Attribute,
    val relationships: Relationship,
) {
    data class Attribute(
        val name: String,
        val rating: String,
        @SerializedName("image-urls")
        val imageUrls: ArrayList<String>,
        val description: String,
        val benefits: String,
        val ingredients: String,
        @SerializedName("how-to-text")
        val howTo: String,
        val price: Double,
        @SerializedName("original-price")
        val originalPrice: Double,
        @SerializedName("under-sale")
        val isSale: Boolean,
        @SerializedName("variants-count")
        val variantCount: Int,
        @SerializedName("option-type-categories")
        val variantType: ArrayList<String>,

    )

    data class Relationship(
        val brand: Brand,
    )

    data class Brand(
        val data: BrandData,
    )

    data class BrandData(
        val type: String,
        val id: Int,
    )
}