package com.kerolosatya.currencyconverter.ui.home.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.kerolosatya.currencyconverter.R
import com.kerolosatya.currencyconverter.data.model.RatesModel
import com.kerolosatya.currencyconverter.data.util.Common
import com.kerolosatya.currencyconverter.databinding.FragmentDetailsBinding
import com.kerolosatya.currencyconverter.ui.base.BaseApiStatus
import com.kerolosatya.currencyconverter.ui.base.BaseFragment
import com.kerolosatya.currencyconverter.ui.home.HomeActivity
import com.kerolosatya.currencyconverter.ui.home.currency.CurrencyAdapter
import java.text.SimpleDateFormat
import java.util.Calendar

class DetailsFragment : BaseFragment() {
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var historicalViewModel: HistoricalViewModel
    private lateinit var currencyAdapter: CurrencyAdapter
    private var base: String = ""
    private var target: String = ""
    private var day1: String = ""
    private var day2: String = ""
    private var day3: String = ""
    private var value1: Float = 0f
    private var value2: Float = 0f
    private var value3: Float = 0f

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(layoutInflater)

        init()
        onClick()

        return binding.root
    }

    private fun init() {
        base = arguments?.getString("base") ?: ""
        target = arguments?.getString("target") ?: ""

        binding.base = base
        binding.target = target

        historicalViewModel = (activity as HomeActivity).historicalViewModel
        observeCurrency()



        historicalViewModel.getHistorical(getDate(0), base, getPopular10())
        showProgressDialog(binding.progressLoading)

    }

    private fun onClick() {
        binding.btnClose.setOnClickListener {
            navigateBack()
        }
    }

    private fun observeCurrency() {

        historicalViewModel.historical.observe(viewLifecycleOwner) {
            when (historicalViewModel.statusHistorical.value) {
                BaseApiStatus.LOADING -> {
                    Log.d(Common.KeroDebug, "Details Api LOADING")
                    showProgressDialog(binding.progressLoading)
                }

                BaseApiStatus.DONE -> {
                    if (!it.success!!) {
                        binding.txtNoData.visibility = View.VISIBLE
                        showToastSnack(it.error!!.info, true)
                        binding.layoutAllData.visibility = View.GONE
                        hideProgressDialog(binding.progressLoading)
                    } else {
                        binding.txtNoData.visibility = View.GONE
                        binding.layoutAllData.visibility = View.VISIBLE
                        when (it.date) {
                            getDate(0) -> {
                                day1 = it.date
                                it.rates!!.toMap().forEach { (key, value) ->
                                    if (key == target) {
                                        value1 = value
                                        return@forEach
                                    }
                                }

                                binding.value1 = value1.toString()
                                binding.date1 = day1

                                setupPopularRecycler(it.rates.toMap())
                                historicalViewModel.getHistorical(getDate(1), base, getPopular10())

                            }

                            getDate(1) -> {
                                day2 = it.date
                                it.rates!!.toMap().forEach { (key, value) ->
                                    if (key == target) {
                                        value2 = value
                                        return@forEach
                                    }
                                }

                                binding.value2 = value2.toString()
                                binding.date2 = day2

                                historicalViewModel.getHistorical(getDate(2), base, getPopular10())

                            }

                            getDate(2) -> {
                                day3 = it.date
                                it.rates!!.toMap().forEach { (key, value) ->
                                    if (key == target) {
                                        value3 = value
                                        return@forEach
                                    }
                                }

                                binding.value3 = value3.toString()
                                binding.date3 = day3

                                hideProgressDialog(binding.progressLoading)
                                setupChart()
                            }

                            else -> {
                                showToastSnack("this date is not valid", true)
                                hideProgressDialog(binding.progressLoading)
                            }
                        }
                    }
                }

                BaseApiStatus.ERROR -> {
                    hideProgressDialog(binding.progressLoading)
                    Log.d(Common.KeroDebug, "Details Api ERROR")
                }

                else -> {
                    Log.d(Common.KeroDebug, "Details Api Nothing")
                }
            }
        }
    }


    private fun setupPopularRecycler(map: Map<String, Float>) {
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        currencyAdapter = CurrencyAdapter()

        currencyAdapter.setData(base, filterCurrencies(map))
        binding.recycler.layoutManager = layoutManager
        binding.recycler.adapter = currencyAdapter
    }


    private fun setupChart() {
        val linevalues = ArrayList<Entry>()
        linevalues.add(Entry(0f, value1))
        linevalues.add(Entry(1f, value2))
        linevalues.add(Entry(2f, value3))

        val linedataset = LineDataSet(linevalues, "Statistics of changes in the $base")

        linedataset.color = resources.getColor(R.color.red)

        linedataset.circleRadius = 10f
        linedataset.circleHoleColor = resources.getColor(R.color.red)
        linedataset.setCircleColor(resources.getColor(R.color.red))
        linedataset.setDrawFilled(true)
        linedataset.valueTextSize = 0F
        linedataset.fillColor = resources.getColor(R.color.yellow)
        linedataset.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        val data = LineData(linedataset)
        binding.chart.data = data
        binding.chart.setBackgroundColor(resources.getColor(R.color.white))
        binding.chart.animateXY(2000, 2000, Easing.EaseInCubic)


    }

    private fun getDate(offset: Int): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -offset)
        val date = calendar.time

        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        return dateFormat.format(date)
    }

    private fun getPopular10(): String {
        return "$target,USD,EGP,AED,SAR,EUR,MAD,KWD,GBP,JPY"
    }

    fun filterCurrencies(inputMap: Map<String, Float>): Map<String, Float> {
        val currenciesToInclude: List<String> = getPopular10().split(",")
        return inputMap.filter { (key, _) -> currenciesToInclude.contains(key) }
    }

}