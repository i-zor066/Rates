package io.csqn.rates.domain.models

data class Rates(
    val baseCurrency: String,
    val rates: HashMap<String, Double>
)