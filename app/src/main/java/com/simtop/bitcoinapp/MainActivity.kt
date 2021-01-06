package com.simtop.bitcoinapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.simtop.testutils.core.selectVisibility
import com.simtop.bitcoinapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Setting view binding for Fragments
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolbar(getString(R.string.app_name),true)
    }

    fun setupToolbar(title: String, activate: Boolean) {
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(activate)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}