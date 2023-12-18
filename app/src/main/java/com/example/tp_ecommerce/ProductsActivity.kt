package com.example.tp_ecommerce

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.content.Intent
import android.widget.Button


class ProductsActivity : AppCompatActivity() {


    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductsAdapter
    private lateinit var categorySpinner: Spinner
    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        categorySpinner = findViewById(R.id.categorySpinner)

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ProductsAdapter(emptyList())
        recyclerView.adapter = adapter

        // Initialisation de Retrofit et de l'interface ApiService
        apiService = Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

        loadCategories()

        // Bouton pour accéder au panier
        val btnViewCart = findViewById<Button>(R.id.btnViewCart)
        btnViewCart.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }
    }


    private fun loadCategories() {
        apiService.getCategories().enqueue(object : Callback<List<String>> {
            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                if (response.isSuccessful) {
                    val categories = mutableListOf("all") // Ajoute "all" comme première option
                    response.body()?.let { categories.addAll(it) }

                    // Met à jour le Spinner avec les catégories reçues
                    val spinnerAdapter = ArrayAdapter(this@ProductsActivity, android.R.layout.simple_spinner_item, categories)
                    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    categorySpinner.adapter = spinnerAdapter

                    // Définition de l'écouteur d'événements pour le Spinner
                    categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                            val selectedCategory = parent.getItemAtPosition(position).toString()
                            if (selectedCategory == "all") {
                                loadProducts() // Charge tous les produits si "all" est sélectionné
                            } else {
                                loadProductsByCategory(selectedCategory) // Sinon charge par catégorie
                            }
                        }

                        override fun onNothingSelected(parent: AdapterView<*>) {
                            // Optionnel : que faire quand rien n'est sélectionné
                        }
                    }
                } else {
                    // TODO: Gérer le cas où la réponse n'est pas réussie
                }
            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                // TODO: Gérer l'échec de l'appel API
            }
        })
    }

    private fun loadProducts() {
        apiService.getProducts().enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if (response.isSuccessful) {
                    // Mise à jour de l'adaptateur avec tous les produits
                    val products = response.body() ?: emptyList()
                    adapter.updateProducts(products)
                } else {
                }
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
            }
        })
    }

    private fun loadProductsByCategory(category: String) {
        apiService.getProductsByCategory(category).enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if (response.isSuccessful) {
                    // Mise à jour de l'adaptateur avec les produits de la catégorie choisie
                    val products = response.body() ?: emptyList()
                    adapter.updateProducts(products)
                } else {
                }
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
            }
        })
    }

}
