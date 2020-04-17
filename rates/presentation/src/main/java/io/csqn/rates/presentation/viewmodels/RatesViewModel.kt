package io.csqn.rates.presentation.viewmodels

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
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.*

class RatesViewModel(
    private val environment: RatesEnvironment
) : BaseViewModel(),
    RatesViewModelInputs,
    RatesViewModelOutputs {

    private val _updateRates = MutableLiveData<Event<RatesEntity>>()
    private val _hideKeyboard = MutableLiveData<Event<Irrelevant>>()
    private lateinit var loadingJob: Job

    val inputs: RatesViewModelInputs = this
    val outputs: RatesViewModelOutputs = this

    //region INPUTS
    override fun onViewCreated() {
        loadPage(Currency.getInstance(Locale.UK).currencyCode)
    }

    override fun onValueEdited(currencyCode: String, value: Double) {
        _hideKeyboard.value = Event(Irrelevant.Instance)
        loadPage(currencyCode, value)
    }

    override fun onKeyboardVisibilityChange(isOpen: Boolean) {
        if (isOpen) {
            loadingJob.cancel()
        }
    }
    //endregion

    private fun loadPage(baseCurrency: String, baseRate: Double = DEFAULT_BASE_RATE) {
        loadingJob = viewModelScope.launch(exceptionHandler) {
            while (isActive) {
                delay(REFRESH_RATE)
                _updateRates.value =
                    Event(environment.getCombinedRatesDataUseCase.invoke(baseCurrency, baseRate))
            }
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

    companion object {
        const val REFRESH_RATE = 1000L
        const val DEFAULT_BASE_RATE = 1.00
    }
}
