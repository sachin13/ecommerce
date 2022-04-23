package com.example.ecommerce.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.ecommerce.R
import com.example.ecommerce.data.model.Products
import com.example.ecommerce.data.room.ProductViewModel
import com.example.ecommerce.databinding.ActivityMainBinding
import com.example.ecommerce.databinding.FragmentBasketBinding


class BasketFragment : Fragment(), BasketAdapter.Listener {
    private lateinit var binding: FragmentBasketBinding
    private val basketAdapter = BasketAdapter(this)
    private lateinit var productViewModel: ProductViewModel
    private lateinit var mainBinding: ActivityMainBinding
    var basketElementNumber = 0



    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mainBinding = DataBindingUtil.inflate(inflater, R.layout.activity_main, container, false)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_basket, container, false)
        binding.BasketRv.adapter = basketAdapter

        productViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        productViewModel.basketProducts.observe(viewLifecycleOwner, Observer {

            var basketSum = 0
            for (element in it) {
                basketSum += element.price?.toInt()!!*element.quantity
            }
            binding.basketSum.text = basketSum.toString() + " TL"
            basketAdapter.setProductList(it)

        })

        return binding.root
    }

    override fun onCLickItem(productItem: Products) {
        if(productItem.quantity>1){
            productItem.quantity--
            productViewModel.updateProduct(productItem)
        }else{
            productItem.quantity=0
            productViewModel.deleteProduct(productItem)
            Toast.makeText(context, "${productItem.name} Sepetten Silindi! ", Toast.LENGTH_SHORT).show()
        }
    }


}