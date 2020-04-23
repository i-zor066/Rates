package io.csqn.rates.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import io.csqn.core.livedata.Event
import io.csqn.core.livedata.Irrelevant
import io.csqn.core.viewmodels.BaseViewModel
import io.csqn.explorer.presentation.di.RatesComponentManager
import io.csqn.rates.domain.entities.RateEntity
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

    private val _updateBaseRate = MutableLiveData<Event<RateEntity>>()
    private val _updateRates = MutableLiveData<Event<List<RateEntity>>>()
    private val _hideKeyboard = MutableLiveData<Event<Irrelevant>>()
    private lateinit var loadingJob: Job

    val inputs: RatesViewModelInputs = this
    val outputs: RatesViewModelOutputs = this

    var activeBaseRateCurrency = Currency.getInstance(Locale.UK).currencyCode
    var activeBaseRateMultiplier = DEFAULT_BASE_RATE

    //region INPUTS
    override fun onViewCreated() {
        loadPage()
    }

    override fun updateBaseRate(currencyCode: String, value: Double) {
        activeBaseRateMultiplier = value
        activeBaseRateCurrency = currencyCode
    }

    override fun switchBaseCurrency(currencyCode: String, value: Double) {
        _hideKeyboard.value = Event(Irrelevant.Instance)
        activeBaseRateMultiplier = value
        activeBaseRateCurrency = currencyCode
        loadPage()
    }

    override fun onKeyboardVisibilityChange(isOpen: Boolean) {
        if (isOpen) {
//            loadingJob.cancel()
        }
    }
    //endregion

    private fun loadPage() {
        loadingJob = viewModelScope.launch(exceptionHandler) {
            while (isActive) {
                delay(REFRESH_RATE)
                val response =
                    environment.getCombinedRatesDataUseCase.invoke(activeBaseRateCurrency)
                _updateBaseRate.value = Event(adjustForMultiplier(activeBaseRateMultiplier, response.baseRate))
                _updateRates.value = Event(response.rates.map { adjustForMultiplier(activeBaseRateMultiplier, it) })
            }
        }
    }

    private fun adjustForMultiplier(multiplier: Double, entity: RateEntity): RateEntity {
        return entity.copy(rate = entity.rate * multiplier)
    }

    //region OUTPUTS
    override val updateBaseRate: LiveData<Event<RateEntity>>
        get() = _updateBaseRate
    override val updateRates: LiveData<Event<List<RateEntity>>>
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
