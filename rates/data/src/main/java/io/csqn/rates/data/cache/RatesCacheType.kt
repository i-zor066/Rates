package io.csqn.rates.data.cache

import io.csqn.rates.data.envelopes.RatesEnvelope
import io.reactivex.Single

interface RatesCacheType {
    fun shouldWriteToCache(): Boolean
    fun saveToCache(ratesEnvelope: RatesEnvelope): Single<RatesEnvelope>
    fun getCachedRatesEnvelopeSingle(): Single<RatesEnvelope>
    fun getCachedRatesEnvelope(): RatesEnvelope
}