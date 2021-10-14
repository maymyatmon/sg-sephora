package com.may.sephora.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.may.sephora.model.ResponseData
import com.may.sephora.network.Repository

class MainViewModel : ViewModel() {

    var productLiveData: MutableLiveData<ResponseData>? = null

    fun getProducts() : LiveData<ResponseData>? {
        productLiveData = Repository.getProductsApiCall()
        return productLiveData
    }

}