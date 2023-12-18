package com.example.tp_ecommerce

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CartAdapter(
    private var items: MutableList<CartItem>,
    private val onUpdate: () -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.titleTextView)
        val priceTextView: TextView = view.findViewById(R.id.priceTextView)
        val quantityTextView: TextView = view.findViewById(R.id.quantityTextView)
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val btnMinus: Button = view.findViewById(R.id.btnMinus)
        val btnPlus: Button = view.findViewById(R.id.btnPlus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartItem = items[position]

        holder.titleTextView.text = cartItem.product.title
        holder.priceTextView.text = "${cartItem.product.price} €"
        holder.quantityTextView.text = "Quantité : ${cartItem.quantity}"
        Glide.with(holder.itemView.context).load(cartItem.product.image).into(holder.imageView)

        holder.btnMinus.setOnClickListener {
            if (cartItem.quantity > 1) {
                Cart.addItem(cartItem.product, -1)
            } else {
                Cart.removeItem(cartItem.product.id)
                items.removeAt(position)
                notifyItemRemoved(position)
            }
            onUpdate.invoke()
            refreshItems()
        }

        holder.btnPlus.setOnClickListener {
            Cart.addItem(cartItem.product, 1)
            onUpdate.invoke()
            refreshItems()
        }

    }

    override fun getItemCount(): Int = items.size

    fun refreshItems() {
        items.clear()
        items.addAll(Cart.getItems())
        notifyDataSetChanged()
    }

}
