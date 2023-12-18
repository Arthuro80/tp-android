package com.example.tp_ecommerce

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id: Int,
    val title: String,
    val price: Double,
    val image: String,
    val description: String
) : Parcelable
