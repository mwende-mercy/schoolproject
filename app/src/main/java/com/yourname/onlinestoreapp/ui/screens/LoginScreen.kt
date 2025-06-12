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
import java.nio.file.WatchEvent

@Composable
fun LoginScreen(navController: NavController, onLoginSuccess: () -> Unit = { navController.navigate(Routes.HOME)}) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome Back 👋",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Please login to your account",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") }, singleLine = true, modifier = Modifier.fillMaxWidth(), shape = MaterialTheme.shapes.medium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Password") }, visualTransformation = PasswordVisualTransformation(), singleLine = true, modifier = Modifier.fillMaxWidth(),shape = MaterialTheme.shapes.medium)

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) navController.navigate("home")
                    else Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT).show()
                }
        },
            modifier = Modifier.fillMaxWidth()
                .height(50.dp),
            shape = MaterialTheme.shapes.medium
            )
        {
            Text("Login")
        }

        Spacer(modifier = Modifier.height(16.dp))
        TextButton(onClick = {
            navController.navigate(Routes.FORGOT_PASSWORD)
        }
        )
        {
            Text("Forgot Password")
        }


        TextButton(onClick = {
            navController.navigate("register") {
                popUpTo("login") { inclusive = true }
            }
        }
        )
        {
            Text("Don't have an account? Register")
        }


        }

}
