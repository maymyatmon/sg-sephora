package com.may.sephora.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.may.sephora.R
import com.may.sephora.adapter.ItemClickListener
import com.may.sephora.adapter.ProductAdapter
import com.may.sephora.model.Product
import com.may.sephora.viewmodel.MainViewModel

class ProductListFragment : Fragment(), ItemClickListener {

    lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val actionbar = (activity as AppCompatActivity).supportActionBar
        //set actionbar title
        actionbar!!.title = "Sephora"
        actionbar.setDisplayHomeAsUpEnabled(false)

        val txtTotal = view.findViewById<TextView>(R.id.txtTotal)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.getProducts()!!.observe(this, Observer { responseData ->

            val productList = responseData.data
            txtTotal.text = "${productList.size} items"

            val gridLayoutManager =
                GridLayoutManager(activity?.applicationContext, 2)
            recyclerView.layoutManager = gridLayoutManager
            recyclerView.adapter =
                ProductAdapter(view.context, productList, responseData.included, this)

        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar menu items
        when (item.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClick(product: Product, brand: String) {
        Log.d("ProductListFragment", product.toString())
        val bundle = Bundle()
        bundle.putInt("id", product.id)
        bundle.putString("brand", brand)
        val productDetailsFragment = ProductDetailsFragment()
        productDetailsFragment.arguments = bundle
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.fragmentContainer, productDetailsFragment)?.addToBackStack(null)
            ?.commit()
    }
}