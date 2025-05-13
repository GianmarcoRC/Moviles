package com.example.mitienda.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mitienda.models.ApiService
import com.example.mitienda.models.MainState
import kotlinx.coroutines.launch

class LoginViewModel(private val mainState: MainState) : ViewModel() {

    private val _state = MutableLiveData<LoginState>()
    val state: LiveData<LoginState> = _state

    fun login(email: String, password: String) {
        _state.value = LoginState.Loading
        viewModelScope.launch {
            try {
                mainState.login(email, password)?.let { tokens ->
                    _state.value = LoginState.Success(tokens.accessToken)
                } ?: run {
                    _state.value = LoginState.Error("Credenciales inválidas")
                }
            } catch (e: Exception) {
                _state.value = LoginState.Error(e.localizedMessage ?: "Error desconocido")
            } finally {

            }
        }
    }
}