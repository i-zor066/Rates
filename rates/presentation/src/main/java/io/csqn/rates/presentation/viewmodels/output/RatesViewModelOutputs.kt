package io.csqn.rates.presentation.viewmodels.output

import androidx.lifecycle.LiveData
import io.csqn.core.livedata.Event
import io.csqn.core.livedata.Irrelevant
import io.csqn.rates.domain.entities.RateEntity
import io.csqn.rates.domain.entities.RatesEntity

interface RatesViewModelOutputs {
    val updateBaseRate: LiveData<Event<RateEntity>>
    val updateRates: LiveData<Event<List<RateEntity>>>
    val hideKeyboard: LiveData<Event<Irrelevant>>
}
