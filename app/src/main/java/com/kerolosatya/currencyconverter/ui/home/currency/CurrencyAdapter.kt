package com.kerolosatya.currencyconverter.ui.home.currency

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kerolosatya.currencyconverter.data.model.CurrencyModel
import com.kerolosatya.currencyconverter.databinding.LayoutPopularCurrenciesBinding

class CurrencyAdapter : RecyclerView.Adapter<CurrencyAdapter.ViewHolder>() {

    private lateinit var ratesMap: Map<String, Float>
    private lateinit var base: String

    fun setData(base: String, ratesMap: Map<String, Float>) {
        this.ratesMap = ratesMap
        this.base = base
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: LayoutPopularCurrenciesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Pair<String, Float>, position: Int) {

            binding.base = base
            binding.currencyName = item.first
            binding.price = item.second.toString()

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutPopularCurrenciesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        ratesMap.toList()?.get(position)?.let { holder.bind(it, position) }
    }

    override fun getItemCount(): Int {
        return ratesMap.keys.toList().size
    }
}