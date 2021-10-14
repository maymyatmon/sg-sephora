package com.may.sephora.network

import com.may.sephora.model.ResponseData
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("products?number=1&size=30&landing_page=sale&include=brand,option_types.option_values,featured_variant,featured_ad&sort=sales")
    fun getProducts() : Call<ResponseData>

}