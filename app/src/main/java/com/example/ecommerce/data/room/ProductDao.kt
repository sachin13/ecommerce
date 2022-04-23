package com.example.ecommerce.data.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.ecommerce.data.model.Products

@Dao
interface ProductDao {

    @Query("SELECT * FROM product_table ORDER BY id ASC")
    fun getProducts(): LiveData<List<Products>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addProduct(Product : Products)

    @Update
    suspend fun updateProduct(Product: Products)

    @Delete
    fun delete(product:Products)

}