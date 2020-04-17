package io.csqn.rates.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import io.csqn.core.livedata.Event
import io.csqn.core.livedata.Irrelevant
import io.csqn.core.viewmodels.BaseViewModel
import io.csqn.explorer.presentation.di.RatesComponentManager
import io.csqn.rates.domain.entities.RatesEntity
import io.csqn.rates.presentation.RatesEnvironment
import io.csqn.rates.presentation.viewmodels.input.RatesViewModelInputs
import io.csqn.rates.presentation.viewmodels.output.RatesViewModelOutputs
import kotlinx.coroutines.*
import java.util.*

class RatesViewModel(
    private val environment: RatesEnvironment
) : BaseViewModel(),
    RatesViewModelInputs,
    RatesViewModelOutputs {

    private val _updateRates = MutableLiveData<Event<RatesEntity>>()
    private val _hideKeyboard = MutableLiveData<Event<Irrelevant>>()
    private var activeBaseRate = Currency.getInstance(Locale.UK).currencyCode

    private lateinit var loadingJob: Job
    val inputs: RatesViewModelInputs = this
    val outputs: RatesViewModelOutputs = this

    //region INPUTS
    override fun onViewCreated() {
        loadPage(activeBaseRate)
    }

    override fun onValueEdited(currencyCode: String, value: Double) {
        _hideKeyboard.value = Event(Irrelevant.Instance)
        loadingJob.children
        activeBaseRate = currencyCode
        loadPage(currencyCode, value)
    }

    override fun onKeyboardVisibilityChange(isOpen: Boolean) {
        if (isOpen) {
            loadingJob.cancel()
        }
    }
    //endregion

    private fun loadPage(baseCurrency: String, baseRate:Double = 1.00) {
       loadingJob = viewModelScope.launch(exceptionHandler) {
            while (isActive) {
                delay(1000)
                if (baseCurrency != activeBaseRate) cancel()
                Log.d("JOB ", "STARTED ${baseCurrency} // $baseRate // key ${loadingJob.key}")
                _updateRates.value =
                    Event(environment.getCombinedRatesDataUseCase.invoke(baseCurrency, baseRate))
                Log.d("JOB ", "ended ${loadingJob.key}")
            }
        }
        loadingJob.invokeOnCompletion {
            Log.d("JOB ", "onCompletion ${loadingJob.key}")
        }
    }

    //region OUTPUTS
    override val updateRates: LiveData<Event<RatesEntity>>
        get() = _updateRates
    override val hideKeyboard: LiveData<Event<Irrelevant>>
        get() = _hideKeyboard
    //endregion

    override fun onCleared() {
        super.onCleared()
        RatesComponentManager.destroy()
    }
}


