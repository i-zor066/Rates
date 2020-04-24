package io.csqn.rates.data.api

import io.csqn.rates.data.envelopes.CountryEnvelope
import io.reactivex.Single

interface CountriesApiType {
    fun getCountry(currency: String): Single<CountryEnvelope>
}
