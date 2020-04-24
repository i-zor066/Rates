package io.csqn.rates.data.api

import io.csqn.rates.data.envelopes.CountryEnvelope
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface CountriesApiService {

    @GET("rest/v2/currency/{code}")
    fun getCountry(@Path("code") code: String): Single<List<CountryEnvelope>>
}
