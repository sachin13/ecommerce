package com.example.ecommerce.data.service

import com.example.ecommerce.data.model.Products
import io.reactivex.Single
import retrofit2.http.GET

interface ApiService {
    @GET("NewProducts.json")
    fun getNewProducts(): Single<List<Products>>

    @GET("PopulerProducts.json")
    fun getPopularProducts(): Single<List<Products>>

}