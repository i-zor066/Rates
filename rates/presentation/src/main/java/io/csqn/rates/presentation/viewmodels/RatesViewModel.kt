package io.csqn.rates.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import io.csqn.core.livedata.Event
import io.csqn.core.livedata.Irrelevant
import io.csqn.core.viewmodels.BaseViewModel
import io.csqn.explorer.presentation.di.RatesComponentManager
import io.csqn.rates.domain.entities.RateEntity
import io.csqn.rates.domain.entities.RatesEntity
import io.csqn.rates.presentation.RatesEnvironment
import io.csqn.rates.presentation.viewmodels.input.RatesViewModelInputs
import io.csqn.rates.presentation.viewmodels.output.RatesViewModelOutputs
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Job
import java.util.*
import java.util.concurrent.TimeUnit

class RatesViewModel(
    private val environment: RatesEnvironment
) : BaseViewModel(),
    RatesViewModelInputs,
    RatesViewModelOutputs {

    private val _updateBaseRate = MutableLiveData<Event<RateEntity>>()
    private val _updateRates = MutableLiveData<Event<List<RateEntity>>>()
    private val _hideKeyboard = MutableLiveData<Event<Irrelevant>>()

    val inputs: RatesViewModelInputs = this
    val outputs: RatesViewModelOutputs = this

    var activeBaseRateCurrency = Currency.getInstance(Locale.UK).currencyCode
    var activeBaseRateMultiplier = DEFAULT_BASE_RATE

    //region INPUTS
    override fun onViewCreated() {
        loadPage()
    }

    override fun updateBaseRate(currencyCode: String, value: Double) {
        Log.d("BASE RATE DEBUG", "updateBaseRate: currencyCode: $currencyCode // value: $value")
        activeBaseRateMultiplier = value
        activeBaseRateCurrency = currencyCode
    }

    override fun switchBaseCurrency(currencyCode: String, value: Double) {
        _hideKeyboard.value = Event(Irrelevant.Instance)
        clearComposite()
        Log.d("BASE RATE DEBUG", "switchBaseCurrency: currencyCode: $currencyCode // value: $value")
        activeBaseRateMultiplier = value
        activeBaseRateCurrency = currencyCode
        loadPage()
    }

    override fun onDone() {
        _hideKeyboard.value = Event(Irrelevant.Instance)
    }
    //endregion

    private fun loadPage() {
        environment.getCombinedRatesDataUseCase
            .invoke(activeBaseRateCurrency)
            .repeatWhen { it.delay(REFRESH_RATE, TimeUnit.SECONDS) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({updateUi(it)}, {rxError(it)}).addToComposite()
    }

    private fun updateUi(ratesEntity: RatesEntity) {
        val updatedBaseRate  = adjustForMultiplier(activeBaseRateMultiplier, ratesEntity.baseRate)
        _updateBaseRate.value =
            Event(
//                adjustForMultiplier(activeBaseRateMultiplier, ratesEntity.baseRate)
            updatedBaseRate
            )
        Log.d("BASE RATE DEBUG", "updateUi: updatedBaseRate: $updatedBaseRate")
        _updateRates.value =
            Event(ratesEntity.rates.map {
                adjustForMultiplier(activeBaseRateMultiplier, it)
            })
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
        const val REFRESH_RATE = 1L
        const val DEFAULT_BASE_RATE = 1.00
    }
}
