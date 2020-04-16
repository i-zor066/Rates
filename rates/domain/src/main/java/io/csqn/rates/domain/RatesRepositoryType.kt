package io.csqn.rates.domain

import io.csqn.rates.domain.models.Rates

interface RatesRepositoryType {
    suspend fun getRates(baseCurrency: String):Rates
}