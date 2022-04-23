package com.example.ecommerce.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ecommerce.data.model.Products


@Database(entities = [Products::class],version = 2,exportSchema = false)
abstract class ProductDatabase: RoomDatabase() {

    abstract fun productDao():ProductDao


    companion object{

        @Volatile
        private var INSTANCE: ProductDatabase? = null

        fun getDatabase(context: Context):ProductDatabase
        {
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProductDatabase::class.java,
                    "product_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }

        }

    }

}