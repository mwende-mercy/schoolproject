package com.yourname.onlinestoreapp.viewmodel

//package com.yourname.onlinestore.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.yourname.onlinestoreapp.model.Product
import java.util.*
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.viewModelScope
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch


class ProductViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    val productList: SnapshotStateList<Product> = mutableStateListOf()
    var isLoading = mutableStateOf(false)
    var errorMessage = mutableStateOf<String?>(null)

    init {
        fetchProducts()
    }



    fun fetchProducts() {
        isLoading.value = true
        db.collection("products")
            .get()
            .addOnSuccessListener { result ->
                productList.clear()
                for (doc in result) {
                    val product = doc.toObject(Product::class.java)
                    productList.add(product)
                }
                isLoading.value = false
            }
            .addOnFailureListener { e ->
                errorMessage.value = e.message
                isLoading.value = false
            }
    }

    fun addProduct(name: String, description: String, price: Double,imageUrl: String? = null, onSuccess: () -> Unit, onError: (String) -> Unit) {
        val id = UUID.randomUUID().toString()
        val product = Product(id, name, description, price)

        db.collection("products").document(id)
            .set(product)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onError(it.message ?: "Unknown error") }
    }

    fun uploadProductImageAndAddProduct(
        name: String,
        description: String,
        price: Double,
        imageUri: Uri,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val storageRef = FirebaseStorage.getInstance().reference
        val imageRef = storageRef.child("product_images/${UUID.randomUUID()}.jpg")

        imageRef.putFile(imageUri)
            .addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                    // Call your product creation method with image URL
                    addProduct(
                        name = name,
                        description = description,
                        price = price,
                        imageUrl = downloadUrl.toString(),
                        onSuccess = onSuccess,
                        onError = onError
                    )
                }
            }
            .addOnFailureListener {
                onError("Image upload failed: ${it.message}")
            }
    }
}
