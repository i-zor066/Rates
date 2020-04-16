package io.csqn.rates.data.api

import io.csqn.rates.data.BuildConfig
import io.csqn.rates.data.envelopes.RatesEnvelope
import retrofit2.Retrofit
import javax.inject.Inject

class RatesApi @Inject constructor(retrofitBuilder: Retrofit.Builder) : RatesApiType {

    private val api: RatesApiService =
        retrofitBuilder.baseUrl(BuildConfig.BASE_URL).build()
            .create(RatesApiService::class.java)

    override suspend fun getRates(baseCurrency: String): RatesEnvelope {
        return api.getRates(baseCurrency)
    }
}
