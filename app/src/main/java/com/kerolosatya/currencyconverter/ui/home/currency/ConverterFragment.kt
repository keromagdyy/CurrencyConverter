package com.kerolosatya.currencyconverter.ui.home.currency

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import com.kerolosatya.currencyconverter.R
import com.kerolosatya.currencyconverter.data.model.RatesModel
import com.kerolosatya.currencyconverter.data.util.Common
import com.kerolosatya.currencyconverter.databinding.FragmentConverterBinding
import com.kerolosatya.currencyconverter.ui.base.BaseApiStatus
import com.kerolosatya.currencyconverter.ui.base.BaseFragment
import com.kerolosatya.currencyconverter.ui.home.HomeActivity
import kotlin.reflect.full.declaredMemberProperties

class ConverterFragment : BaseFragment() {
    private lateinit var binding: FragmentConverterBinding
    private lateinit var currencyViewModel: CurrencyViewModel
    private lateinit var currencies: RatesModel
    private var valueFrom: Float = 0f
    private var valueTo: Float = 0f

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConverterBinding.inflate(layoutInflater)
        init()
        onClick()

        return binding.root
    }

    private fun init() {

        currencyViewModel = (activity as HomeActivity).currencyViewModel
        showProgressDialog(binding.progressLoading)
        observeCompetitions()
    }

    private fun onClick() {
        binding.btnDetails.setOnClickListener {
            navigateTo(R.id.detailsFragment, null)
        }
        binding.txtListFrom.setOnItemClickListener { _, _, position, _ ->

            val item = binding.txtListFrom.adapter.getItem(position).toString()

            currencies.toMap().forEach { (key, value) ->
                if (item == key) {
                    valueFrom = value
                    return@forEach
                }
            }
            binding.txtFrom.setText("")
            binding.txtTo.setText("")
        }
        binding.txtListTo.setOnItemClickListener { _, _, position, _ ->

            val item = binding.txtListTo.adapter.getItem(position).toString()

            currencies.toMap().forEach { (key, value) ->
                if (item == key) {
                    valueTo = value
                    return@forEach
                }
            }
            binding.txtFrom.setText("")
            binding.txtTo.setText("")
        }
        binding.btnConvert.setOnClickListener {
            convertTexts()
        }
        binding.txtFrom.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(txt: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(txt: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (txt!!.isNotBlank() && txt!!.isNotEmpty() && binding.txtFrom.hasFocus()) {
                    val amount = binding.txtFrom.text.toString().toFloat()
                    val resultTo = convertBetweenCurrencies(amount, valueFrom, valueTo)
                    binding.txtTo.setText(resultTo.toString())
                }
            }

            override fun afterTextChanged(txt: Editable?) {

            }

        })
        binding.txtTo.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(txt: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(txt: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (txt!!.isNotBlank() && txt!!.isNotEmpty() && binding.txtTo.hasFocus()) {
                    val amount = binding.txtTo.text.toString().toFloat()
                    val resultFrom = convertBetweenCurrencies(amount, valueTo, valueFrom)
                    binding.txtFrom.setText(resultFrom.toString())
                }
            }

            override fun afterTextChanged(txt: Editable?) {

            }

        })
    }

    private fun observeCompetitions() {
        currencyViewModel.currency.observe(viewLifecycleOwner) {
            when (currencyViewModel.statusCurrency.value) {
                BaseApiStatus.LOADING -> {
                    Log.d(Common.KeroDebug, "Currencies Api LOADING")
                    showProgressDialog(binding.progressLoading)
                }

                BaseApiStatus.DONE -> {
                    hideProgressDialog(binding.progressLoading)
                    if (it.success!!) {
                        binding.txtNoData.visibility = View.GONE

                        currencies = it.rates!!
                        setupAutoCompleteAdapter()
                    } else {
                        binding.txtNoData.visibility = View.VISIBLE
                    }

                    Log.d(Common.KeroDebug, "Currencies Api DONE: ${it}")

                }

                BaseApiStatus.ERROR -> {
                    hideProgressDialog(binding.progressLoading)
                    binding.txtNoData.visibility = View.VISIBLE
                    Log.d(Common.KeroDebug, "Currencies Api ERROR")
                }

                else -> {
                    Log.d(Common.KeroDebug, "Currencies Api Nothing")
                }
            }
        }

    }

    private fun setupAutoCompleteAdapter() {
//        val resources = requireContext().resources
//        val currencyArray = resources.getStringArray(R.array.currencies)
//        val currencyList: List<String> = currencyArray.toList()


        val currencyArray = currencies.toMap().keys.toList()
        val adapter =
            ArrayAdapter(requireActivity(), android.R.layout.simple_list_item_1, currencyArray)

        binding.txtListFrom.setAdapter(adapter)
        binding.txtListTo.setAdapter(adapter)
    }

    private fun convertTexts() {
        val counterList = binding.txtListFrom.text
        binding.txtListFrom.text = binding.txtListTo.text
        binding.txtListTo.text = counterList

        binding.txtFrom.setText("")
        binding.txtTo.setText("")

        val counterValue = valueFrom
        valueFrom = valueTo
        valueTo = counterValue
    }

    fun convertBetweenCurrencies(amount: Float, sourceRate: Float, targetRate: Float): Float {
        val amountInEUR = amount / sourceRate
        return amountInEUR * targetRate
    }

    private fun RatesModel.toMap(): Map<String, Float> {
        return this::class.declaredMemberProperties
            .associateBy { it.name }
            .mapValues { prop ->
                prop.value.call(this) as Float
            }
    }

}