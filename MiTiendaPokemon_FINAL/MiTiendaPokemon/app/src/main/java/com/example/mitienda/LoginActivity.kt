package com.example.mitienda

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.mitienda.databinding.ActivityLoginBinding
import com.example.mitienda.models.MainState
import com.example.mitienda.models.TokenProviderImpl
import com.example.mitienda.viewmodels.LoginState
import com.example.mitienda.viewmodels.LoginViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tokenProvider = TokenProviderImpl(this)
        val mainState = MainState(tokenProvider)
        loginViewModel = LoginViewModel(mainState)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnlogin.setOnClickListener {
            val email = binding.Email.text.toString()
            val password = binding.Password.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                Log.d("LoginActivity", "Click en login: $email / $password")
                Toast.makeText(this, "Intentando login", Toast.LENGTH_SHORT).show()
                loginViewModel.login(email, password)
                binding.progressBar.visibility = View.VISIBLE
                binding.btnlogin.isEnabled = false
            } else {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        loginViewModel.state.observe(this) { state ->
            when (state) {
                is LoginState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.btnlogin.isEnabled = false
                }

                is LoginState.Success -> {
                    TokenProviderImpl(this).saveToken(state.token)
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }

                is LoginState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.btnlogin.isEnabled = true
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
