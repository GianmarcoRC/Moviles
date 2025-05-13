package com.example.mitienda.viewmodels

sealed class LoginState {
    object Loading : LoginState()
    data class Success(val token: String) : LoginState()
    data class Error(val message: String) : LoginState()
}