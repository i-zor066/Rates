package io.csqn.rates.data.api

import io.csqn.rates.data.BuildConfig
import io.csqn.rates.data.envelopes.RatesEnvelope
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.Retrofit
import javax.inject.Inject

class RatesApi @Inject constructor(retrofitBuilder: Retrofit.Builder) : RatesApiType {

    private val api: RatesApiService =
        retrofitBuilder.baseUrl(BuildConfig.BASE_URL).build()
            .create(RatesApiService::class.java)

    override fun getRates(baseCurrency: String): Single<RatesEnvelope> {
        return api.getRates(baseCurrency)
    }
}
