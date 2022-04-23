package com.example.ecommerce.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.ecommerce.R
import com.example.ecommerce.data.ProductDataSource
import com.example.ecommerce.data.model.Products
import com.example.ecommerce.data.model.Status
import com.example.ecommerce.data.room.ProductViewModel
import com.example.ecommerce.databinding.BasketItemBinding
import com.example.ecommerce.databinding.ProductsFragmentBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ProductsFragment : Fragment(), ProductsAdapter.Listener {

    private val productDataSource = ProductDataSource()
    private lateinit var binding: ProductsFragmentBinding
    private val newProductsAdapter = ProductsAdapter(this)
    private val popularProductsAdapter = ProductsAdapter(this)
    private lateinit var productViewModel: ProductViewModel
    private lateinit var basketItemBinding : BasketItemBinding
    private var basketItemList : List<Products> = emptyList()


    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        productDataSource
            .fetchNewProducts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                when (it.status) {
                    Status.SUCCESS -> {
                        newProductsAdapter.setProductList(it.data!!)
                        binding.newProductProgressBar.visibility = View.GONE
                    }
                    Status.LOADING -> {
                        binding.newProductProgressBar.visibility = View.VISIBLE
                    }
                }
            }

        productDataSource.fetchPopularProducts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                when (it.status) {
                    Status.SUCCESS -> {
                        popularProductsAdapter.setProductList(it.data!!)
                        binding.popularProductsProgressBar.visibility = View.GONE
                    }
                    Status.LOADING -> {
                        binding.popularProductsProgressBar.visibility = View.VISIBLE
                    }
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.products_fragment, container, false)
        basketItemBinding = DataBindingUtil.inflate(inflater, R.layout.basket_item, container, false)
        productViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)

        binding.popularProductsRecyclerView.adapter = popularProductsAdapter
        binding.newProductsRecyclerView.adapter = newProductsAdapter
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onCLickItem(productItem: Products) {
        productViewModel.basketProducts.observe(viewLifecycleOwner, Observer {
            basketItemList = it
            Log.d("Bs", basketItemList.toString())
        })

        if (basketItemList.contains(productItem)){
            productItem.quantity+=1
            productViewModel.updateProduct(productItem)
            Toast.makeText(context, "${productItem.name} Sepete Eklendi! ", Toast.LENGTH_SHORT).show()

        }else{
            productItem.quantity+=1
            productViewModel.addProduct(productItem)
            Toast.makeText(context, "${productItem.name} Sepete Eklendi! ", Toast.LENGTH_SHORT).show()
        }

    }

}