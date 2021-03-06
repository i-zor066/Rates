package io.csqn.rates.presentation

import io.csqn.rates.domain.usecases.GetCombinedRatesDataUseCase
import io.reactivex.Scheduler
import javax.inject.Inject

class RatesEnvironment @Inject constructor(
    val getCombinedRatesDataUseCase: GetCombinedRatesDataUseCase
)