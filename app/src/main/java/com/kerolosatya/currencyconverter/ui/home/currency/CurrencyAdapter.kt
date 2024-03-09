package com.kerolosatya.currencyconverter.ui.home.currency

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kerolosatya.currencyconverter.data.model.CurrencyModel
import com.kerolosatya.currencyconverter.databinding.LayoutPopularCurrenciesBinding

class CurrencyAdapter : RecyclerView.Adapter<CurrencyAdapter.ViewHolder>() {

    private var competitionsList = ArrayList<CurrencyModel>()

    fun setData(list: List<CurrencyModel>) {
        competitionsList = list as ArrayList
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: LayoutPopularCurrenciesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CurrencyModel, position: Int) {

            binding.base = item.base

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
        competitionsList?.get(position)?.let { holder.bind(it, position) }
    }

    override fun getItemCount(): Int {
        return competitionsList.size
    }
}