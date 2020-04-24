package io.csqn.rates.data.api

import io.csqn.rates.data.envelopes.RatesEnvelope
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RatesApiService {

    @GET("latest")
    fun getRates(@Query("base", encoded = true) baseCurrency: String): Single<RatesEnvelope>
}
