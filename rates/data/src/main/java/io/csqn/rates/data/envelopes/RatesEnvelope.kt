package io.csqn.rates.data.envelopes

import kotlin.collections.HashMap

data class RatesEnvelope(
    val baseCurrency: String,
    val rates: HashMap<String, Double>
)
