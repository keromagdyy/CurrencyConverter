package com.kerolosatya.currencyconverter.ui.home.historical

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kerolosatya.currencyconverter.data.model.CurrencyModel
import com.kerolosatya.currencyconverter.domain.useCase.HistoricalUseCase
import com.kerolosatya.currencyconverter.ui.base.BaseApiStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoricalViewModel @Inject constructor(
    private val app: Application,
    private val historicalUseCase: HistoricalUseCase,
) : ViewModel() {

    private val _statusHistorical = MutableLiveData<BaseApiStatus>()
    val statusHistorical: LiveData<BaseApiStatus>
        get() = _statusHistorical

    private var _historical = MutableLiveData<CurrencyModel>()
    val historical: LiveData<CurrencyModel>
        get() = _historical


    fun getHistorical(
        date: String,
        base: String,
        symbols: String,
    ) {
        _statusHistorical.postValue(BaseApiStatus.LOADING)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _statusHistorical.postValue(BaseApiStatus.DONE)

                _historical.postValue(historicalUseCase.execute(date, base, symbols))
                Log.d("getHistoricalException", "getHistorical: ${historical}")

            } catch (e: Exception) {
                _statusHistorical.postValue(BaseApiStatus.ERROR)
                Log.d("getHistoricalException", "getHistorical: ${e.message}")
            }

        }
    }

}