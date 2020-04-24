package io.csqn.rates.data.api

import io.csqn.rates.data.envelopes.RatesEnvelope
import io.reactivex.Flowable
import io.reactivex.Single

interface RatesApiType {
    fun getRates(baseCurrency: String): Single<RatesEnvelope>
}
