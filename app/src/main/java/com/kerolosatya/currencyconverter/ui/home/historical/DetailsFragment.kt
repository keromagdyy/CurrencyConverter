package com.kerolosatya.currencyconverter.ui.home.historical

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.transform.CircleCropTransformation
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.android.material.tabs.TabLayout
import com.kerolosatya.currencyconverter.R
import com.kerolosatya.currencyconverter.data.model.CurrencyModel
import com.kerolosatya.currencyconverter.data.util.Common
import com.kerolosatya.currencyconverter.databinding.FragmentDetailsBinding
import com.kerolosatya.currencyconverter.ui.base.BaseApiStatus
import com.kerolosatya.currencyconverter.ui.base.BaseFragment
import com.kerolosatya.currencyconverter.ui.home.HomeActivity
import com.kerolosatya.currencyconverter.ui.home.currency.CurrencyAdapter

class DetailsFragment : BaseFragment() {
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var historicalViewModel: HistoricalViewModel
    private lateinit var currencyAdapter: CurrencyAdapter
    private var competitionId: String = ""

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
        competitionId = arguments?.getString("id") ?: ""
        
        setupChart()

        historicalViewModel = (activity as HomeActivity).historicalViewModel
//        historicalViewModel.getHistorical(competitionId)
        showProgressDialog(binding.progressLoading)

    }

    private fun onClick() {
        binding.btnBack.setOnClickListener {
            navigateBack()
        }
    }

    private fun observeCurrency() {

        historicalViewModel.historical.observe(viewLifecycleOwner) {
            when (historicalViewModel.statusHistorical.value) {
                BaseApiStatus.LOADING -> {
                    Log.d(Common.KeroDebug, "CompetitionDetails Api LOADING")
                    showProgressDialog(binding.progressLoading)
                }
                BaseApiStatus.DONE -> {
                    hideProgressDialog(binding.progressLoading)


//                    setupPopularRecycler()
//                    Log.d(Common.KeroDebug, "CompetitionDetails Api Done: ${teamsList}")
                }

                BaseApiStatus.ERROR -> {
                    hideProgressDialog(binding.progressLoading)
                    Log.d(Common.KeroDebug, "CompetitionDetails Api ERROR")
                }

                else -> {
                    Log.d(Common.KeroDebug, "CompetitionDetails Api Nothing")
                }
            }
        }
    }


//    private fun setupPopularRecycler(model: CurrencyModel) {
//        val layoutManager =
//            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
//        currencyAdapter = CurrencyAdapter()
//
//        currencyAdapter.setData(model)
//        binding.recycler.layoutManager = layoutManager
//        binding.recycler.adapter = currencyAdapter
//    }


    private fun setupChart() {
        val linevalues = ArrayList<Entry>()
        linevalues.add(Entry(20f, 0.0F))
        linevalues.add(Entry(30f, 3.0F))
        linevalues.add(Entry(40f, 2.0F))
        linevalues.add(Entry(50f, 1.0F))
        linevalues.add(Entry(60f, 8.0F))
        linevalues.add(Entry(70f, 10.0F))
        linevalues.add(Entry(80f, 1.0F))
        linevalues.add(Entry(90f, 2.0F))
        linevalues.add(Entry(100f, 5.0F))
        linevalues.add(Entry(110f, 1.0F))
        linevalues.add(Entry(120f, 20.0F))
        linevalues.add(Entry(130f, 40.0F))
        linevalues.add(Entry(140f, 50.0F))

        val linedataset = LineDataSet(linevalues, "First")

        linedataset.color = resources.getColor(R.color.red)

        linedataset.circleRadius = 10f
        linedataset.setDrawFilled(true)
        linedataset.valueTextSize = 20F
        linedataset.fillColor = resources.getColor(R.color.yellow)
        linedataset.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        val data = LineData(linedataset)
        binding.chart.data = data
        binding.chart.setBackgroundColor(resources.getColor(R.color.white))
        binding.chart.animateXY(2000, 2000, Easing.EaseInCubic)


    }

}