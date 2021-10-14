package com.may.sephora.model

data class Included(
    val type: String,
    val id: Int = 0,
    val attributes: Attribute) {
    data class Attribute(
        val name: String,
    )
}