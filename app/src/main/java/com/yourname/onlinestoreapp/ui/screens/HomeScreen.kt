package com.yourname.onlinestoreapp.ui.screens
//package com.yourname.onlinestore.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yourname.onlinestoreapp.model.Product
import com.yourname.onlinestoreapp.viewmodel.CartViewModel
import com.yourname.onlinestoreapp.viewmodel.ProductViewModel

@Composable
fun HomeScreen(
    productViewModel: ProductViewModel = viewModel(),
    cartViewModel: CartViewModel = viewModel()
) {
    val products = productViewModel.productList
    val isLoading by productViewModel.isLoading
    val errorMessage by productViewModel.errorMessage

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("All Products", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            CircularProgressIndicator()
        } else if (errorMessage != null) {
            Text(text = errorMessage ?: "Unknown error", color = MaterialTheme.colorScheme.error)
        } else if (products.isEmpty()) {
            Text("No products available.")
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxHeight()
            ) {
                items(products) { product ->
                    ProductItem(product = product, cartViewModel = cartViewModel)
                }
            }
        }
    }
}

@Composable
fun ProductItem(product: Product, cartViewModel: CartViewModel) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(product.name, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))

            Text(product.description, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))

            Text("Price: Ksh ${product.price}", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    cartViewModel.addToCart(product)
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Add to Cart")
            }
        }
    }
}

