package com.may.sephora.model

data class ResponseData(
    val data: List<Product>,
    val included: List<Included>,
)