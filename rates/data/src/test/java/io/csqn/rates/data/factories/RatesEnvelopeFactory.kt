package io.csqn.rates.data.factories

import io.csqn.rates.data.envelopes.RatesEnvelope

object RatesEnvelopeFactory {
    fun create(): RatesEnvelope {
        return RatesEnvelope(
            "baseCurrency",
            hashMapOf(
                "currency1" to 0.01,
                "currency2" to 0.02
            )
        )
    }
}
