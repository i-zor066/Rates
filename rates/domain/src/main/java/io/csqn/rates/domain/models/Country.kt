package io.csqn.rates.domain.models

data class Country(
    val countryCode: String,
    val currencyCode: String,
    val currencyName: String,
    val flag: String
)