package io.csqn.rates.data.envelopes


data class CountryEnvelope(
    val alpha2Code: String,
    val currencies: List<CurrencyEnvelope>,
    val flag: String
)
