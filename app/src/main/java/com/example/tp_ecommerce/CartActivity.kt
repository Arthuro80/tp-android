package com.example.tp_ecommerce

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CartActivity : AppCompatActivity() {
    private lateinit var adapter: CartAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var tvTotalPrice: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        recyclerView = findViewById(R.id.cartRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        tvTotalPrice = findViewById(R.id.tvTotalPrice)

        adapter = CartAdapter(Cart.getItems(), this::updateTotalPrice)
        recyclerView.adapter = adapter

        val btnReturn = findViewById<Button>(R.id.btnReturn)
        btnReturn.setOnClickListener {
            finish()
        }

        updateTotalPrice()
    }

    override fun onResume() {
        super.onResume()
        adapter.refreshItems()
        updateTotalPrice()
    }

    private fun updateTotalPrice() {
        val totalPrice = Cart.getTotalPrice()
        tvTotalPrice.text = getString(R.string.total_price, totalPrice)
    }
}


