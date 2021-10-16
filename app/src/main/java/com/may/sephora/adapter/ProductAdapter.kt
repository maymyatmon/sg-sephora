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
import com.may.sephora.view.ProductHelper
import java.util.jar.Attributes

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

        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_product_item, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val product = productList[position]
        val attributes = product.attributes

        val url = attributes.imageUrls[0]
        if (!url.isNullOrEmpty())
            Glide.with(context).load(url).into(holder.imageView)

        holder.txtBrand.text = ProductHelper.getBrand(product, includedList)

        holder.txtName.text = attributes.name

        val originalPrice = attributes.originalPrice
        val price = attributes.price
        if (attributes.isSale) {
            holder.txtOriginalPrice.text = ProductHelper.getFormattedPrice(originalPrice)
            holder.txtOriginalPrice.paintFlags =
                holder.txtOriginalPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            holder.txtPrice.text = ProductHelper.getFormattedPrice(price)
            holder.txtPercentage.text = "(-${ProductHelper.getPercentage(originalPrice, price)}%)"
        } else {
            holder.txtOriginalPrice.text = ProductHelper.getFormattedPrice(price)
            holder.txtOriginalPrice.typeface = Typeface.DEFAULT_BOLD
        }

        holder.ratingBar.rating = attributes.rating.toFloat()

        val variantCount = attributes.variantCount
        var variantType = attributes.variantType[0]
        if (variantCount > 1) variantType += "s"
        holder.txtVariants.text = "$variantCount $variantType"

        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(product, holder.txtBrand.text.toString())
        }
    }

    override fun getItemCount() = productList.size
}

interface ItemClickListener {
    fun onItemClick(product: Product, brand: String)
}