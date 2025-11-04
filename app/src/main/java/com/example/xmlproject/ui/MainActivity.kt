package com.example.xmlproject.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.xmlproject.R
import com.example.xmlproject.data.api.RetrofitClient
import com.example.xmlproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        RetrofitClient.init(this)

        if (savedInstanceState == null) {
            loadFragment(SignInFragment(), showBottomNav = false)

        }

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> loadFragment(HomePageFragment())
                R.id.nav_trip_history -> loadFragment(TripListFragment())
                R.id.nav_wallet -> loadFragment(WalletFragment())
            }
            true
        }
    }

    fun loadFragment(fragment: Fragment, showBottomNav: Boolean = true) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()

        binding.bottomNavigation.visibility = if (showBottomNav) View.VISIBLE else View.GONE
    }

    fun saveAuthToken(token: String) {
        val sharedPref = getSharedPreferences("MyAppPrefs", MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("AUTH_TOKEN", token)
            apply()
        }
    }

    fun getAuthToken(): String? {
        val sharedPref = getSharedPreferences("MyAppPrefs", MODE_PRIVATE)
        return sharedPref.getString("AUTH_TOKEN", null)
    }

}

