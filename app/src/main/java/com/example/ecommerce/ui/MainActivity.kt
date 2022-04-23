package com.example.ecommerce.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.example.ecommerce.R
import com.example.ecommerce.data.room.ProductViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var productViewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        productViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)

        val navController = Navigation.findNavController(this, R.id.fragmentContainerView)
        findViewById<BottomNavigationView>(R.id.bottomNav).setupWithNavController(navController)

        var bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNav)

        bottomNavigationView.getOrCreateBadge(R.id.basketFragment).apply {
            productViewModel.basketProducts.observe(this@MainActivity, Observer {
                number = it.size
            })

        }


    }
}