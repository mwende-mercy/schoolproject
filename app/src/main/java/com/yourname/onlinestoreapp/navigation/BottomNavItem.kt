package com.yourname.onlinestoreapp.navigation

//package com.yourname.onlinestore.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    object Home : BottomNavItem("home", "Home", Icons.Filled.Home)
    object Cart : BottomNavItem("cart", "Cart", Icons.Filled.ShoppingCart)
    object Profile : BottomNavItem("profile", "Profile", Icons.Filled.Person)
}
