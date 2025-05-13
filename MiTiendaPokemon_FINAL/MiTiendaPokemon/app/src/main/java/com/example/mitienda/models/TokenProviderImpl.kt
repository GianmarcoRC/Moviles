package com.example.mitienda.models

import android.content.Context

class TokenProviderImpl(private val context: Context) : MainState.TokenProvider {
    private val sharedPreferences = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    override fun getToken(): String? {
        return sharedPreferences.getString("access_token", null)
    }

    fun saveToken(accessToken: String) {
        sharedPreferences.edit().putString("access_token", accessToken).apply()
    }

    fun clearToken() {
        sharedPreferences.edit().remove("access_token").apply()
    }
}