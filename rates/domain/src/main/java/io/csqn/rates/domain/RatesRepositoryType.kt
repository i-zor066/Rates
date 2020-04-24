package io.csqn.rates.domain

import io.csqn.rates.domain.models.Rates
import io.reactivex.Flowable
import io.reactivex.Single

interface RatesRepositoryType {
    fun getRates(baseCurrency: String): Single<Rates>
}