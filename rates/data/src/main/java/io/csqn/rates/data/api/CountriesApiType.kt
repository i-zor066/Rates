package io.csqn.rates.data.api

import io.csqn.rates.data.envelopes.CountryEnvelope

interface CountriesApiType {
    suspend fun getCountry(currency: String): CountryEnvelope
}
