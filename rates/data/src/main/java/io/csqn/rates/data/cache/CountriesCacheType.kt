package io.csqn.rates.data.cache

import io.csqn.rates.data.envelopes.CountryEnvelope
import io.reactivex.Single

interface CountriesCacheType {
    fun isCached(currencyCode: String): Boolean
    fun isCachedSingle(currencyCode: String): Single<Boolean>
    fun saveToCache(currencyCode: String, countryEnvelope: CountryEnvelope): Single<CountryEnvelope>
    fun getCountryFromCache(currencyCode: String): Single<CountryEnvelope>
}
