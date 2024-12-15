package com.example.uas.admin

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
import com.example.uas.databinding.ActivityAdminBinding

class AdminActivity : AppCompatActivity() {
    private val binding by lazy { ActivityAdminBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        val navController = findNavController(R.id.admin_nav_host)

        binding.adminBottomNavigationView.setupWithNavController(navController)
        binding.logOut.setOnClickListener{
            performLogout()
        }
    }

    private fun performLogout() {
        PrefManager.getInstance(this).clear()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    companion object{
        fun getLaunchService(from: Context) = Intent(from, AdminActivity::class.java)
    }
}