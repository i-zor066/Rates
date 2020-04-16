package io.csqn.rates.presentation.viewmodels.output

import androidx.lifecycle.LiveData
import io.csqn.core.livedata.Event
import io.csqn.rates.domain.entities.RatesEntity

interface RatesViewModelOutputs {
    val updateRates: LiveData<Event<RatesEntity>>
}