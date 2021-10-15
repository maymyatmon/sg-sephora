package com.may.sephora.adapter

import android.content.Context
import android.graphics.Paint
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.may.sephora.R
import com.may.sephora.model.Included
import com.may.sephora.model.Product

class ProductAdapter(
    private val context: Context,
    private var productList: List<Product>,
    private var includedList: List<Included>,
    private val itemClickListener: ItemClickListener
) : RecyclerView.Adapter<ProductAdapter.ItemViewHolder>() {

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val txtBrand: TextView = view.findViewById(R.id.txtBrand)
        val txtName: TextView = view.findViewById(R.id.txtName)
        val txtOriginalPrice: TextView = view.findViewById(R.id.txtOriginalPrice)
        val txtPrice: TextView = view.findViewById(R.id.txtPrice)
        val txtPercentage: TextView = view.findViewById(R.id.txtPercentage)
        val ratingBar: RatingBar = view.findViewById(R.id.ratingBar)
        val txtVariants: TextView = view.findViewById(R.id.txtVariants)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_product_item, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val product = productList[position]

        val url = product.attributes.imageUrls[0]
        if (!url.isNullOrEmpty())
            Glide.with(context).load(url).into(holder.imageView)

        holder.txtBrand.text = getBrand(product)

        holder.txtName.text = product.attributes.name

        bindPrice(product, holder)

        holder.ratingBar.rating = product.attributes.rating.toFloat()

        val variantCount = product.attributes.variantCount
        var variantType = product.attributes.variantType[0]
        if (variantCount > 1) variantType += "s"
        holder.txtVariants.text = "$variantCount $variantType"

        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(product, holder.txtBrand.text.toString())
        }
    }

    private fun getBrand(product: Product): String {
        val list =
            includedList.filter { it.type == product.relationships.brand.data.type && it.id == product.relationships.brand.data.id }
        val included = list[0]
        return included.attributes.name
    }

    private fun bindPrice(product: Product, holder: ItemViewHolder) {
        val originalPrice = product.attributes.originalPrice
        if (product.attributes.isSale) {
            val price = product.attributes.price
            val percentage = (originalPrice - price) / originalPrice * 100

            holder.txtOriginalPrice.text = "$$originalPrice"
            holder.txtOriginalPrice.paintFlags =
                holder.txtOriginalPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            holder.txtPrice.text = "$$price"
            holder.txtPercentage.text = "(-${percentage.toInt()}%)"
        } else {
            holder.txtOriginalPrice.text = "$ $originalPrice"
            holder.txtOriginalPrice.typeface = Typeface.DEFAULT_BOLD
        }
    }


    override fun getItemCount() = productList.size
}

interface ItemClickListener {
    fun onItemClick(product: Product, brand: String)
}