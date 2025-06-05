package com.yourname.onlinestoreapp.navigation

//package com.yourname.onlinestore.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.yourname.onlinestoreapp.ui.screens.AddProductScreen
import com.yourname.onlinestoreapp.ui.screens.CartScreen
import com.yourname.onlinestoreapp.ui.screens.LoginScreen
import com.yourname.onlinestoreapp.ui.screens.RegisterScreen
import com.yourname.onlinestoreapp.ui.screens.HomeScreen
import com.yourname.onlinestoreapp.ui.screens.ProfileScreen

object Routes {
    const val ADD_PRODUCT = "add_product"
    const val CART = "cart"
    const val PROFILE = "profile"
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val HOME = "home"
}

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.LOGIN) {
        composable(Routes.ADD_PRODUCT) {
            AddProductScreen(
                onProductAdded = {
                    // Go back or to home
                }
            )
        }

        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = { navController.navigate(Routes.HOME) }
            )
        }
        composable(Routes.REGISTER) {
            RegisterScreen(
                onRegisterSuccess = { navController.navigate(Routes.HOME) }
            )
        }
        composable(Routes.HOME) {
            HomeScreen()
        }
        composable(Routes.CART) {
            CartScreen()
        }
        composable(Routes.PROFILE) {
            ProfileScreen(navController)
        }

    }
}
