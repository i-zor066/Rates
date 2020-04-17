package io.csqn.rates.data.factories

import io.csqn.rates.data.envelopes.CurrencyEnvelope

object CurrencyEnvelopeFactory {
    fun create(isEuro: Boolean = false): CurrencyEnvelope {
        return CurrencyEnvelope(
            if (isEuro) "EUR" else "GBP",
            if (isEuro) "Euro" else "British pound"
        )
    }
}
