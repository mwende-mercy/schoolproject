package com.yourname.onlinestoreapp.viewmodel

//package com.yourname.onlinestore.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    // Add these at the top inside the class:
    private val _isLoginSuccessful = MutableStateFlow(false)
    val isLoginSuccessful: StateFlow<Boolean> = _isLoginSuccessful
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _isRegisterSuccessful = MutableStateFlow(false)
    val isRegisterSuccessful: StateFlow<Boolean> = _isRegisterSuccessful

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun registerUser(email: String, password: String) {
        viewModelScope.launch {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    _isRegisterSuccessful.value = task.isSuccessful
                    if (!task.isSuccessful) {
                        _errorMessage.value = task.exception?.message
                    }
                }
        }
    }

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    _isLoginSuccessful.value = task.isSuccessful
                    if (!task.isSuccessful) {
                        _errorMessage.value = task.exception?.message
                    }
                }
        }
    }



}
