package com.example.ecommerce.data.room

import androidx.lifecycle.LiveData
import com.example.ecommerce.data.model.Products

class ProductRepository(private val productDao : ProductDao) {

    val allProducts : LiveData<List<Products>> = productDao.getProducts()

    suspend fun addProduct(product: Products){
        productDao.addProduct(product)
    }

    suspend fun updateProduct(product: Products){
        productDao.updateProduct(product)
    }

    suspend fun deleteProduct(product: Products){
        productDao.delete(product)
    }

}