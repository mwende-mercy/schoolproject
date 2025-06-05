package com.yourname.onlinestoreapp.model

//package com.yourname.onlinestore.model

data class CartItem(
    val product: Product,
    var quantity: Int = 1
)
