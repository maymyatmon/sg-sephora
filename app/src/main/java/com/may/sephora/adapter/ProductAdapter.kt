package com.may.sephora.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.may.sephora.R
import com.may.sephora.model.Product

class ProductAdapter(
    private var productList: List<Product>,
    private val itemClickListener: ItemClickListener
) : RecyclerView.Adapter<ProductAdapter.ItemViewHolder>() {

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val txtName: TextView = view.findViewById(R.id.txt_name)
        val txtPrice: TextView = view.findViewById(R.id.txt_price)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_product_item, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val product = productList[position]
        holder.txtName.text = product.attributes.name
        holder.txtPrice.text = "$ ${product.attributes.price.toString()}"

        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(product)
        }
    }

    override fun getItemCount() = productList.size
}

interface ItemClickListener {
    fun onItemClick(product: Product)
}