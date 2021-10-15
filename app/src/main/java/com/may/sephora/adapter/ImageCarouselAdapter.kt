package com.may.sephora.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.viewpager.widget.PagerAdapter
import com.may.sephora.R
import java.util.*
import kotlin.collections.ArrayList
import com.bumptech.glide.Glide


internal class ImageCarouselAdapter(
    var context: Context,
    var images: ArrayList<String>
) : PagerAdapter() {

    var mLayoutInflater: LayoutInflater

    override fun getCount(): Int {
        return images.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as LinearLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView: View = mLayoutInflater.inflate(R.layout.layout_image_item, container, false)

        val imageView: ImageView = itemView.findViewById(R.id.carouselImageView)

        val url = images[position]
        if (!url.isNullOrEmpty())
            Glide.with(context).load(url).into(imageView)

        Objects.requireNonNull(container).addView(itemView)
        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }

    init {
        images = images
        mLayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }
}