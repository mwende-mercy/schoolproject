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
import com.yourname.onlinestoreapp.viewmodel.CartViewModel

@Composable
fun CartScreen(cartViewModel: CartViewModel = viewModel()) {
    val cartItems = cartViewModel.cartItems

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Your Cart", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        if (cartItems.isEmpty()) {
            Text("Your cart is empty.")
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(cartItems) { item ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(item.product.name, style = MaterialTheme.typography.titleMedium)
                            Text("Quantity: ${item.quantity}")
                            Text("Price: Ksh ${item.product.price * item.quantity}")
                            Button(onClick = {cartViewModel.removeFromCart(item.product.id)}) {
                                Text("Remove from cart")
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("Total: Ksh ${cartViewModel.getTotalPrice()}", style = MaterialTheme.typography.titleLarge)

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { /* TODO: Proceed to checkout */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Checkout")
            }
        }
    }
}
