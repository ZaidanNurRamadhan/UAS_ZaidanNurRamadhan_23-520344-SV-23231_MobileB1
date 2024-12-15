package com.example.uas

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

class PrefManager private constructor(context : Context){
    private val sharedPref : SharedPreferences

    companion object {
        private const val PREF_NAME = "myPref"
        private const val IS_LOGIN = "IS_LOGIN"
        private const val NAME = "NAME"
        private const val EMAIL = "EMAIL"
        private const val ROLE = "USER"
        private const val PASSWORD = "PASSWORD"

        @Volatile
        private var instance : PrefManager? = null
        fun getInstance(context: Context) : PrefManager {
            return instance ?: synchronized(this) {
                instance ?: PrefManager(context).also {
                    instance = it
                }
            }
        }
    }

    init {
        sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun clear() {
        sharedPref.edit()
            .clear() // Hapus semua data
            .apply()
        Log.d("PrefManager", "Semua data di PrefManager telah dihapus.")
    }


    fun setLogin(isLogin : Boolean) {
        sharedPref.edit().putBoolean(IS_LOGIN, isLogin).apply()
    }

    fun getLogin() : Boolean {
        return sharedPref.getBoolean(IS_LOGIN, false)
    }

    fun setUsername(username : String) {
        sharedPref.edit().putString(NAME, username).apply()
    }

    fun getUsername() : String? {
        return sharedPref.getString(NAME, "")
    }

    fun setPassword(password : String) {
        sharedPref.edit().putString(PASSWORD, password).apply()
    }

    fun getPassword() : String? {
        return sharedPref.getString(PASSWORD, null)
    }

    fun getEmail() : String? {
        return sharedPref.getString(EMAIL, "")
    }
    fun setEmail(email : String) {
        sharedPref.edit().putString(EMAIL,email).apply()
    }

    fun setRole(role : String) {
        sharedPref.edit().putString(ROLE, role).apply()
    }

    fun getRole() : String? {
        return sharedPref.getString(ROLE, null)
    }
}