package com.example.tp_ecommerce;

import android.os.Parcelable;
import kotlinx.parcelize.Parcelize;

@Parcelize
class CartItem(
    val product: Product,
    var quantity: Int
) : Parcelable
