package io.csqn.rates.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import io.csqn.core.livedata.Event
import io.csqn.core.viewmodels.BaseViewModel
import io.csqn.explorer.presentation.di.RatesComponentManager
import io.csqn.rates.presentation.viewmodels.output.RatesViewModelOutputs
import io.csqn.rates.domain.entities.RatesEntity
import io.csqn.rates.presentation.RatesEnvironment
import io.csqn.rates.presentation.viewmodels.input.RatesViewModelInputs
import kotlinx.coroutines.launch
import java.util.*

class RatesViewModel(
    private val environment: RatesEnvironment
) : BaseViewModel(),
    RatesViewModelInputs,
    RatesViewModelOutputs {

    private val _updateRates = MutableLiveData<Event<RatesEntity>>()

    val inputs: RatesViewModelInputs = this
    val outputs: RatesViewModelOutputs = this
    private val defaulBaseRate = Currency.getInstance(Locale.UK).currencyCode

    //region INPUTS
    override fun onViewCreated() {
        loadPage(defaulBaseRate)
    }
    //endregion

    private fun loadPage(defaultBaseCurrency: String) {
//        viewModelScope.launch (exceptionHandler) {
        viewModelScope.launch {//TODO launch with exception handler
            _updateRates.value =
                Event(environment.getCombinedRatesDataUseCase.invoke(defaultBaseCurrency))
        }.setLoadingState()
    }

    //region OUTPUTS
    override val updateRates: LiveData<Event<RatesEntity>>
        get() = _updateRates
    //endregion

    override fun onCleared() {
        super.onCleared()
        RatesComponentManager.destroy()
    }
}
