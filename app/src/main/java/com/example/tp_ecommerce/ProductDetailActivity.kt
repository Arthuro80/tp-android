package com.example.tp_ecommerce

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.tp_ecommerce.Cart.addItem

internal class ProductDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        // Récupérer l'objet Product passé par l'intent
        val product = intent.getParcelableExtra<Product>("product")
        // Vérification de nullité pour l'objet Product
        if (product == null) {
            finish()
            return
        }

        // Configure les vues avec les informations du produit
        val imageView = findViewById<ImageView>(R.id.productImageView)
        val titleView = findViewById<TextView>(R.id.productTitleView)
        val priceView = findViewById<TextView>(R.id.productPriceView)
        val descriptionView =
            findViewById<TextView>(R.id.productDescriptionView) // Vue pour la description
        val addToCartButton = findViewById<Button>(R.id.addToCartButton) // Bouton d'ajout au panier
        titleView.text = product.title
        priceView.text = getString(R.string.product_price, product.price)
        descriptionView.text = product.description // Afficher la description
        Glide.with(this).load(product.image).into(imageView)

        // Configure le bouton d'ajout au panier
        addToCartButton.setOnClickListener { view: View? ->
            addItem(product)
            Toast.makeText(
                this@ProductDetailActivity,
                "Produit ajouté au panier",
                Toast.LENGTH_SHORT
            ).show()
        }

        // Configure le bouton de retour
        val backButton = findViewById<Button>(R.id.backButton)
        backButton.setOnClickListener { view: View? -> finish() } // Termine l'activité et revient à l'activité précédente
    }
}