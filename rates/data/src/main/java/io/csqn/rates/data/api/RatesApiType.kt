package io.csqn.rates.data.api

import io.csqn.rates.data.envelopes.RatesEnvelope

interface RatesApiType {
    suspend fun getRates(baseCurrency: String): RatesEnvelope
}
