package io.csqn.rates.data.factories

import io.csqn.rates.data.envelopes.CountryEnvelope

object CountryEnvelopeFactory {
    fun create(containsEuro: Boolean = false): CountryEnvelope {
        return CountryEnvelope(
            if (containsEuro) "EU" else "alpha2Code",
            listOf(
                CurrencyEnvelopeFactory.create(containsEuro),
                CurrencyEnvelopeFactory.create(containsEuro)
            ),
            "flag"
        )
    }
}
