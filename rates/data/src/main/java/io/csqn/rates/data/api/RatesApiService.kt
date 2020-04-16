package io.csqn.rates.data.api

import io.csqn.rates.data.envelopes.RatesEnvelope
import retrofit2.http.GET
import retrofit2.http.Query

interface RatesApiService {

    @GET("latest")
    suspend fun getRates(@Query("base", encoded = true) baseCurrency: String): RatesEnvelope
}
