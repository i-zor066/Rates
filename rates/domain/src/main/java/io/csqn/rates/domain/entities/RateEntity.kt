package io.csqn.rates.domain.entities

data class RateEntity(
    val code: String,
    val currencyCode: String,
    val name: String,
    val flagUrl: String,
    val rate: Double
)
