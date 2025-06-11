package com.yourname.onlinestoreapp.ui.screens

//package com.yourname.onlinestore.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.yourname.onlinestoreapp.navigation.Routes
import com.yourname.onlinestoreapp.viewmodel.AuthViewModel

@Composable
fun RegisterScreen(navController: NavController, onRegisterSuccess: () -> Unit = { navController.navigate(Routes.HOME) }) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Register", style = MaterialTheme.typography.headlineMedium)

        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
        OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Password") })

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) navController.navigate("home")
                    else Toast.makeText(context, "Registration failed", Toast.LENGTH_SHORT).show()
                }
        }) {
            Text("Register")
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = {
            navController.navigate("login") {
                popUpTo("register") { inclusive = true }
            }
        }) {
            Text("Already have an account? Login")
        }
    }
}
