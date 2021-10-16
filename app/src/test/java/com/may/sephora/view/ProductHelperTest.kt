package com.may.sephora.view

import org.junit.Test

import org.junit.Assert.*

class ProductHelperTest {

    @Test
    fun getPercentage() {

        val percentage = ProductHelper.getPercentage(33.00,26.40)
        assertEquals(20, percentage)
    }

    @Test
    fun getFormattedPrice() {
        val formattedPrice = ProductHelper.getFormattedPrice(26.4)
        assertEquals("$26.40", formattedPrice)
    }
}