package com.yourname.onlinestoreapp.ui.screens

////package com.yourname.onlinestore.ui.screens
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import com.yourname.onlinestoreapp.viewmodel.ProductViewModel
//
//@Composable
//fun AddProductScreen(
//    onProductAdded: () -> Unit,
//    viewModel: ProductViewModel = viewModel()
//) {
//    var name by remember { mutableStateOf("") }
//    var description by remember { mutableStateOf("") }
//    var price by remember { mutableStateOf("") }
//    var errorMessage by remember { mutableStateOf<String?>(null) }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(24.dp),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text("Add Product", style = MaterialTheme.typography.headlineMedium)
//        Spacer(modifier = Modifier.height(16.dp))
//
//        OutlinedTextField(
//            value = name,
//            onValueChange = { name = it },
//            label = { Text("Product Name") },
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        OutlinedTextField(
//            value = description,
//            onValueChange = { description = it },
//            label = { Text("Description") },
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        OutlinedTextField(
//            value = price,
//            onValueChange = { price = it },
//            label = { Text("Price") },
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Button(
//            onClick = {
//                val parsedPrice = price.toDoubleOrNull()
//                if (name.isNotBlank() && description.isNotBlank() && parsedPrice != null) {
//                    viewModel.addProduct(
//                        name,
//                        description,
//                        parsedPrice,
//                        onSuccess = {
//                            name = ""
//                            description = ""
//                            price = ""
//                            errorMessage = null
//                            onProductAdded()
//                        },
//                        onError = { error -> errorMessage = error }
//                    )
//                } else {
//                    errorMessage = "Please fill all fields correctly."
//                }
//            },
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text("Add Product")
//        }
//
//        errorMessage?.let {
//            Spacer(modifier = Modifier.height(12.dp))
//            Text(text = it, color = MaterialTheme.colorScheme.error)
//        }
//    }
//}
//package com.yourname.onlinestoreapp.ui.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toFile
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.yourname.onlinestoreapp.viewmodel.ProductViewModel

@Composable
fun AddProductScreen(
    onProductAdded: () -> Unit,
    viewModel: ProductViewModel = viewModel()
) {
    val context = LocalContext.current
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Add Product 🛒", style = MaterialTheme.typography.headlineLarge)

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Product Name") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = price,
            onValueChange = { price = it },
            label = { Text("Price (Ksh)") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Image Picker Button
        OutlinedButton(
            onClick = { imagePickerLauncher.launch("image/*") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(Icons.Filled.Photo, contentDescription = "Pick Image")
            Spacer(modifier = Modifier.width(8.dp))
            Text("Select Product Image")
        }

        // Show selected image
        imageUri?.let { uri ->
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = rememberAsyncImagePainter(uri),
                contentDescription = "Selected Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val parsedPrice = price.toDoubleOrNull()
                if (name.isNotBlank() && description.isNotBlank() && parsedPrice != null) {
                    if (imageUri != null) {
                        viewModel.uploadProductImageAndAddProduct(
                            name = name,
                            description = description,
                            price = parsedPrice,
                            imageUri = imageUri!!,
                            onSuccess = {
                                name = ""
                                description = ""
                                price = ""
                                imageUri = null
                                errorMessage = null
                                onProductAdded()
                            },
                            onError = { errorMessage = it }
                        )
                    } else {
                        viewModel.addProduct(
                            name = name,
                            description = description,
                            price = parsedPrice,
                            imageUrl = null,
                            onSuccess = {
                                name = ""
                                description = ""
                                price = ""
                                imageUri = null
                                errorMessage = null
                                onProductAdded()
                            },
                            onError = { errorMessage = it }
                        )
                    }
                } else {
                    errorMessage = "Please fill all fields correctly."
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Product")
        }

//        Button(
//            onClick = {
//                val parsedPrice = price.toDoubleOrNull()
//                if (name.isNotBlank() && description.isNotBlank() && parsedPrice != null && imageUri != null) {
//                    // Upload logic (you can add Firebase Storage support here)
//                    viewModel.addProduct(
//                        name,
//                        description,
//                        parsedPrice,
//                        onSuccess = {
//                            name = ""
//                            description = ""
//                            price = ""
//                            imageUri = null
//                            errorMessage = null
//                            Toast.makeText(context, "Product added!", Toast.LENGTH_SHORT).show()
//                            onProductAdded()
//                        },
//                        onError = { error -> errorMessage = error }
//                    )
//                } else {
//                    errorMessage = "Please fill all fields and pick an image."
//                }
//            },
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(50.dp),
//            shape = RoundedCornerShape(12.dp)
//        ) {
//            Text("Add Product")
//        }

        errorMessage?.let {
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }
    }
}
