package io.csqn.rates.domain.models

data class Rates(
    val baseCurrency: String,
    val rates: LinkedHashMap<String, Double>
)