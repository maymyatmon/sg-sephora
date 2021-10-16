package com.may.sephora.view

import android.graphics.Paint
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.may.sephora.R
import com.may.sephora.adapter.ImageCarouselAdapter
import com.may.sephora.adapter.ThumbnailAdapter
import com.may.sephora.adapter.ThumbnailClickListener
import com.may.sephora.viewmodel.MainViewModel

private const val ARG_PARAM1 = "id"
private const val ARG_PARAM2 = "brand"

class ProductDetailsFragment : Fragment(), ThumbnailClickListener {
    private var id: Int? = 0
    private var brand: String? = null
    private lateinit var viewPager: ViewPager
    lateinit var thumbnailAdapter: ThumbnailAdapter

    private lateinit var mainViewModel: MainViewModel

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

        val actionbar = (activity as AppCompatActivity).supportActionBar
        //set actionbar title
        actionbar!!.title = brand
        actionbar.setDisplayHomeAsUpEnabled(true)

        bindData(view)
    }

    private fun bindData(view: View) {
        viewPager = view.findViewById(R.id.viewPager)
        val thumbnailRecyclerView = view.findViewById<RecyclerView>(R.id.thumbnailRecyclerView)
        val txtBrand: TextView = view.findViewById(R.id.txtBrand)
        val txtName: TextView = view.findViewById(R.id.txtName)
        val txtOriginalPrice: TextView = view.findViewById(R.id.txtOriginalPrice)
        val txtPrice: TextView = view.findViewById(R.id.txtPrice)
        val txtPercentage: TextView = view.findViewById(R.id.txtPercentage)
        val ratingBar: RatingBar = view.findViewById(R.id.ratingBar)
        val txtDescription: TextView = view.findViewById(R.id.txtDescription)
        val txtBenefit: TextView = view.findViewById(R.id.txtBenefit)
        val txtIngredients: TextView = view.findViewById(R.id.txtIngredients)
        val txtHowTo: TextView = view.findViewById(R.id.txtHowTo)

        this.mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.getProducts()!!.observe(this, Observer { responseData ->

            val list = responseData.data.filter { it.id == id }
            val product = list[0]
            val productAttributes = product.attributes

            //Photo carousel
            val imageCarouselAdapter =
                ImageCarouselAdapter(view.context, productAttributes.imageUrls)
            viewPager.adapter = imageCarouselAdapter
            viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

                override fun onPageScrollStateChanged(state: Int) {
                }

                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                }

                override fun onPageSelected(position: Int) {
                    thumbnailAdapter.updateSelectedIndex(position)
                }
            })

            //photo thumbnail
            thumbnailAdapter = ThumbnailAdapter(view.context, productAttributes.thumbnailUrls, this)
            thumbnailRecyclerView.adapter = thumbnailAdapter

            txtBrand.text = ProductHelper.getBrand(product, responseData.included)
            txtName.text = productAttributes.name

            val originalPrice = productAttributes.originalPrice
            val price = productAttributes.price
            if (productAttributes.isSale) {
                txtOriginalPrice.text = ProductHelper.getFormattedPrice(originalPrice)
                txtOriginalPrice.paintFlags = txtOriginalPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                txtPrice.text = ProductHelper.getFormattedPrice(price)
                txtPercentage.text = "(-${ProductHelper.getPercentage(originalPrice, price)}%)"
            } else {
                txtOriginalPrice.text = ProductHelper.getFormattedPrice(price)
                txtOriginalPrice.typeface = Typeface.DEFAULT_BOLD
            }

            ratingBar.rating = productAttributes.rating.toFloat()

            txtDescription.text = ProductHelper.getSpannedText(productAttributes.description)
            txtBenefit.text = ProductHelper.getSpannedText(productAttributes.benefits)
            txtIngredients.text = ProductHelper.getSpannedText(productAttributes.ingredients)
            txtHowTo.text = ProductHelper.getSpannedText(productAttributes.howTo)
        })
    }

    override fun onThumbnailClick(position: Int) {
        thumbnailAdapter.updateSelectedIndex(position)
        viewPager.setCurrentItem(position, true)
    }
}
