package com.yourname.onlinestoreapp.ui.screens

//package com.yourname.onlinestore.ui.screens

import android.annotation.SuppressLint
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
fun RegisterScreen(navController: NavController, onRegisterSuccess: () -> Unit = { navController.navigate(Routes.HOME) }) {
    var username by remember { mutableStateOf("") }
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
            text = "Create Account 📝",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Sign up to get started",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(value = username, onValueChange = {username = it }, label = {Text("Username")}, singleLine = true, shape = MaterialTheme.shapes.medium, modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth(), singleLine = true, shape = MaterialTheme.shapes.medium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Password") }, visualTransformation = PasswordVisualTransformation(), modifier = Modifier.fillMaxWidth(),singleLine = true, shape = MaterialTheme.shapes.medium)

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {if (email.isBlank() || password.length < 6) {
            Toast.makeText(context, "Please enter a valid email and a password with at least 6 characters",
                Toast.LENGTH_SHORT).show();return@Button
        }
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task -> if(task.isSuccessful) {
                    Toast.makeText(context, "Registration Successful", Toast.LENGTH_SHORT).show()
                onRegisterSuccess()
                }
                else {
                val errorMessage =task.exception?.message ?: "Registration failed"
                    Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                }
                }
        },
            modifier = Modifier.fillMaxWidth()
                .height(50.dp),
            shape = MaterialTheme.shapes.medium
        )
        {
            Text("Register")
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = {
            navController.navigate("login") {
                popUpTo("register") { inclusive = true }
            }
        }
        ) {
            Text("Already have an account? Login")
        }
    }
}
