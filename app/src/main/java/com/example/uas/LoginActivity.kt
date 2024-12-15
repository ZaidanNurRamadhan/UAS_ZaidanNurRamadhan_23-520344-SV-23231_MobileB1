package com.example.uas

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.uas.admin.AdminActivity
import com.example.uas.databinding.ActivityLoginBinding
import com.example.uas.network.ApiClient
import com.example.uas.model.Users
import com.example.uas.user.UserActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    private lateinit var prefManager: PrefManager
    private val apiService by lazy { ApiClient.getInstance() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        prefManager = PrefManager.getInstance(this)
        checkLoginStatus()

        with(binding) {
            loginButton.setOnClickListener {
                val email = email.text.toString()
                val password = password.text.toString()
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(this@LoginActivity, "Email and password must be filled", Toast.LENGTH_SHORT).show()
                } else {
                    loginUser(email, password)
                }
            }

            registerLink.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
                finish()
            }
        }
    }

    fun checkLoginStatus() {
        if (prefManager.getLogin()) {
            if (prefManager.getRole() == "ADMIN") {
                startActivity(AdminActivity.getLaunchService(this))
            }else if (prefManager.getRole() == "USER") {
                startActivity(UserActivity.getLaunchService(this))
            }
            finish()
        }
    }

    private fun loginUser(email : String, password : String) {
        apiService.getAllUsers().enqueue(object : Callback<List<Users>> {
            override fun onResponse(call: Call<List<Users>>, response: Response<List<Users>>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        for (user in body) {
                            if (user.email == email && user.password == password) {
                                prefManager.setLogin(true)
                                if (user.role == "ADMIN") {
                                    startActivity(AdminActivity.getLaunchService(this@LoginActivity))
                                    prefManager.setRole("ADMIN")
                                    prefManager.setUsername(user.name)
                                    finish()
                                } else if (user.role == "USER") {
                                    startActivity(UserActivity.getLaunchService(this@LoginActivity))
                                    prefManager.setRole("USER")
                                    prefManager.setUsername(user.name)
                                    prefManager.setEmail(user.email)
                                    finish()
                                }
                            }
                        }
                    }
                } else {
                    Toast.makeText(this@LoginActivity, "Failed", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<List<Users>>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}