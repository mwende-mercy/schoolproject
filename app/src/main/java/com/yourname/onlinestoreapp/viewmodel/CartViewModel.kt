package com.yourname.onlinestoreapp.viewmodel

//package com.yourname.onlinestore.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.yourname.onlinestoreapp.model.CartItem
import com.yourname.onlinestoreapp.model.Product

class CartViewModel : ViewModel() {
    val cartItems = mutableStateListOf<CartItem>()

    fun addToCart(product: Product) {
        val existing = cartItems.find { it.product.id == product.id }
        if (existing != null) {
            existing.quantity++
        } else {
            cartItems.add(CartItem(product))
        }
    }

    fun removeFromCart(productId: String) {
        cartItems.removeAll { it.product.id == productId }
    }

    fun getTotalPrice(): Double {
        return cartItems.sumOf { it.product.price * it.quantity }
    }

    fun clearCart() {
        cartItems.clear()
    }
}
