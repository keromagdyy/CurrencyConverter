package com.kerolosatya.currencyconverter.ui.home

import android.os.Bundle
import com.kerolosatya.currencyconverter.databinding.ActivityHomeBinding
import com.kerolosatya.currencyconverter.ui.base.BaseActivity
import com.kerolosatya.currencyconverter.ui.home.details.HistoricalViewModel
import com.kerolosatya.currencyconverter.ui.home.currency.CurrencyViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : BaseActivity() {
    private lateinit var binding: ActivityHomeBinding

    @Inject
    lateinit var currencyViewModel : CurrencyViewModel
    @Inject
    lateinit var historicalViewModel : HistoricalViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        onClick()
    }

    private fun init() {

    }

    private fun onClick() {

    }
}