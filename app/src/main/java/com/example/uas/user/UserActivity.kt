package com.example.uas.user

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.uas.LoginActivity
import com.example.uas.PrefManager
import com.example.uas.R
import com.example.uas.databinding.ActivityUserBinding

class UserActivity : AppCompatActivity() {
    private val binding by lazy { ActivityUserBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        val navController = findNavController(R.id.user_nav_host)

        binding.userBottomNavigationView.setupWithNavController(navController)
        binding.logOut.setOnClickListener{
            performLogout()
        }
    }

    private fun performLogout() {
        PrefManager.getInstance(this).clear()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    companion object {
        fun getLaunchService(from: Context) = Intent(from, UserActivity::class.java)
    }
}