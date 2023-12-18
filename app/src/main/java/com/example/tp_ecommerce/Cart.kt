package com.example.tp_ecommerce;

import java.util.HashMap;

object Cart {
    private val items: MutableMap<Int, CartItem> = HashMap()

    fun addItem(product: Product, quantity: Int = 1) {
        if (items.containsKey(product.id)) {
            val newQuantity = (items[product.id]?.quantity ?: 0) + quantity
            if (newQuantity <= 0) {
                items.remove(product.id)
            } else {
                items[product.id]?.quantity = newQuantity
            }
        } else if (quantity > 0) {
            items[product.id] = CartItem(product, quantity)
        }
    }



    fun removeItem(productId: Int) {
        items.remove(productId)
    }


    fun getItems(): MutableList<CartItem> {
        return ArrayList(items.values) // Convertit en MutableList
    }

    fun getTotalPrice(): Double {
        return items.values.sumByDouble { it.product.price * it.quantity }
    }


}
