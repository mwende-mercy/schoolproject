package com.yourname.onlinestoreapp.ui.screens

//package com.yourname.onlinestore.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yourname.onlinestoreapp.viewmodel.ProductViewModel

@Composable
fun AddProductScreen(
    onProductAdded: () -> Unit,
    viewModel: ProductViewModel = viewModel()
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Add Product", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Product Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = price,
            onValueChange = { price = it },
            label = { Text("Price") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val parsedPrice = price.toDoubleOrNull()
                if (name.isNotBlank() && description.isNotBlank() && parsedPrice != null) {
                    viewModel.addProduct(
                        name,
                        description,
                        parsedPrice,
                        onSuccess = {
                            name = ""
                            description = ""
                            price = ""
                            errorMessage = null
                            onProductAdded()
                        },
                        onError = { error -> errorMessage = error }
                    )
                } else {
                    errorMessage = "Please fill all fields correctly."
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Product")
        }

        errorMessage?.let {
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }
    }
}
