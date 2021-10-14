package com.may.sephora.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.may.sephora.R
import com.may.sephora.adapter.ItemClickListener
import com.may.sephora.adapter.ProductAdapter
import com.may.sephora.model.Product
import com.may.sephora.viewmodel.MainViewModel

class ProductListFragment : Fragment(), ItemClickListener {

    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.getProducts()!!.observe(this, Observer { responseData ->

            val productList = responseData.data
            recyclerView.adapter = ProductAdapter(productList, this)

        })
    }

    override fun onItemClick(product: Product) {
        Log.d("ProductListFragment", product.toString())
    }
}