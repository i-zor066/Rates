package io.csqn.rates.data.envelopes

data class RatesEnvelope(
    val baseCurrency: String,
    val rates: LinkedHashMap<String, Double>
)
