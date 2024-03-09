package com.kerolosatya.currencyconverter.ui.home.currency

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kerolosatya.currencyconverter.data.model.CurrencyModel
import com.kerolosatya.currencyconverter.data.util.Common
import com.kerolosatya.currencyconverter.domain.useCase.CurrencyUseCase
import com.kerolosatya.currencyconverter.ui.base.BaseApiStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val app: Application,
    private val currencyUseCase: CurrencyUseCase,
) : ViewModel() {

    private val _statusCurrency = MutableLiveData<BaseApiStatus>()
    val statusCurrency: LiveData<BaseApiStatus>
        get() = _statusCurrency

    private var _currency = MutableLiveData<CurrencyModel>()
    val currency: LiveData<CurrencyModel>
        get() = _currency

    init {
        getCurrencies()
    }

    private fun getCurrencies() {
        viewModelScope.launch(Dispatchers.IO) {
            _statusCurrency.postValue(BaseApiStatus.LOADING)
            try {
                _statusCurrency.postValue(BaseApiStatus.DONE)

                _currency.postValue(currencyUseCase.execute())
                Log.d(Common.KeroDebug, "getCurrencies: ${currency}")

            } catch (e: Exception) {
                _statusCurrency.postValue(BaseApiStatus.ERROR)
                Log.d(Common.KeroDebug, "getCurrencies: ${e.message}")
            }

        }
    }

}