package com.may.sephora.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.may.sephora.R
import com.may.sephora.viewmodel.MainViewModel

private const val ARG_PARAM1 = "id"
private const val ARG_PARAM2 = "brand"

class ProductDetailsFragment : Fragment() {
    private var id: Int? = 0
    private var brand: String? = null

    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getInt(ARG_PARAM1)
            brand = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        (activity as AppCompatActivity).supportActionBar?.title = brand

        bindData(view)
    }

    private fun bindData(view: View) {
        val txtName: TextView = view.findViewById(R.id.txt_name)
        val txtPrice: TextView = view.findViewById(R.id.txt_price)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.getProducts()!!.observe(this, Observer { responseData ->

            val list = responseData.data.filter { it.id == id }
            val product = list[0]

            txtName.text = product.attributes.name
            txtPrice.text = product.attributes.price.toString()
        })
    }
}