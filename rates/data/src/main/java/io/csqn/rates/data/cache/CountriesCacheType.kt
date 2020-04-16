package io.csqn.rates.data.cache

import io.csqn.rates.data.envelopes.CountryEnvelope

interface CountriesCacheType {
    suspend fun isCached(currencyCode: String): Boolean
    suspend fun saveToCache(currencyCode: String, countryEnvelope: CountryEnvelope):CountryEnvelope
    suspend fun getCountryFromCache(currencyCode: String):CountryEnvelope
}