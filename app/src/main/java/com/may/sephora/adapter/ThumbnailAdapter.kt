package com.may.sephora.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.may.sephora.R

class ThumbnailAdapter(
    private val context: Context,
    private var images: ArrayList<String>,
    private val thumbnailClickListener: ThumbnailClickListener
) : RecyclerView.Adapter<ThumbnailAdapter.ItemViewHolder>() {

    var selectedIndex: Int = 0

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val thumbnailContainer: RelativeLayout = view.findViewById(R.id.thumbnailContainer)
        val imageView: ImageView = view.findViewById(R.id.thumbnailImageView)
    }

    fun updateSelectedIndex(selectedIndex: Int) {
        this.selectedIndex = selectedIndex
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_thumbnail_item, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        holder.thumbnailContainer.setBackgroundResource(
            if (selectedIndex == position) R.drawable.rounded_border_pink
            else R.drawable.rounded_border_gray
        )

        val url = images[position]
        if (!url.isNullOrEmpty())
            Glide.with(context).load(url).into(holder.imageView)

        holder.itemView.setOnClickListener {
            thumbnailClickListener.onThumbnailClick(position)
        }
    }

    override fun getItemCount() = images.size
}

interface ThumbnailClickListener {
    fun onThumbnailClick(position: Int)
}