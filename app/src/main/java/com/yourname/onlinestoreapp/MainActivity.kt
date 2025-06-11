package com.yourname.onlinestoreapp
//package com.yourname.onlinestore

//package com.yourname.onlinestore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.yourname.onlinestoreapp.navigation.Routes
import com.yourname.onlinestoreapp.ui.screens.HomeScreen
import com.yourname.onlinestoreapp.ui.screens.LoginScreen
import com.yourname.onlinestoreapp.ui.theme.OnlineStoreAppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent  {
            OnlineStoreAppTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    val user = FirebaseAuth.getInstance().currentUser

                    if (user != null) {
                        // ✅ Show the HomeScreen with Bottom Navigation
                        HomeScreen()
                    } else {
                        // 🔐 Not logged in, show Login Screen
                        LoginScreen(navController)
                    }
                }
            }
        }
    }
}


