package com.example.dunipool

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dunipool.databinding.ActivityMarketBinding

class MarketActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMarketBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivityMarketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.moduleToolbar.mainToolbar)
    }
}