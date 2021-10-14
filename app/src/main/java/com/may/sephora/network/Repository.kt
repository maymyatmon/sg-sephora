package com.may.sephora.network

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.may.sephora.model.ResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object Repository {

    val responseData = MutableLiveData<ResponseData>()

    fun getProductsApiCall(): MutableLiveData<ResponseData> {

        val call = RetrofitClient.apiInterface.getProducts()

        call.enqueue(object: Callback<ResponseData> {
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                Log.v("DEBUG : ", t.message.toString())
            }

            override fun onResponse(
                call: Call<ResponseData>,
                response: Response<ResponseData>
            ) {
                Log.v("DEBUG : ", response.body().toString())

                responseData.value = response.body()
            }
        })

        return responseData
    }
}