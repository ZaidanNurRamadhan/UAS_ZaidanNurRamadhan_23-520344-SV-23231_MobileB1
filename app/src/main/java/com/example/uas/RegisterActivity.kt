package com.example.uas

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.uas.databinding.ActivityRegisterBinding
import com.example.uas.network.ApiClient
import com.example.uas.data.response.RegisterResponse
import com.example.uas.model.Users
import retrofit2.Call
import retrofit2.Callback

class RegisterActivity : AppCompatActivity() {
    private val binding by lazy { ActivityRegisterBinding.inflate(layoutInflater) }
    private val apiService by lazy { ApiClient.getInstance() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        with(binding){
            registerButton.setOnClickListener {
                val name = username.text.toString()
                val email = email.text.toString()
                val password = password.text.toString()
                val role = "USER"

                if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                    register(name, email, password,role)
                } else {
                    Toast.makeText(this@RegisterActivity, "Please fill all the field", Toast.LENGTH_SHORT).show()
                }
            }

            loginLink.setOnClickListener {
                startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                finish()
            }
        }
    }

    private fun register(name: String, email: String, password: String, role: String) {
        // Call the API
        val request = Users(name, email, password, role)

        apiService.createUser(request).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: retrofit2.Response<RegisterResponse>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && body.success) {
                        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                        Toast.makeText(this@RegisterActivity, body.message, Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@RegisterActivity, body?.message ?: "Registration failed", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@RegisterActivity, "Failed to register", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Toast.makeText(this@RegisterActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}