package com.yourname.onlinestoreapp.model

//package com.yourname.onlinestore.model

data class Product(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val price: Double = 0.0,
    val imageUrl: String? = null // You can leave this empty for now
)
