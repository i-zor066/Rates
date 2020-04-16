package io.csqn.rates.data.api

import io.csqn.rates.data.BuildConfig
import io.csqn.rates.data.envelopes.CountryEnvelope
import retrofit2.Retrofit
import javax.inject.Inject

class CountriesApi @Inject constructor(builder: Retrofit.Builder) : CountriesApiType {

    private val api: CountriesApiService =
        builder.baseUrl(BuildConfig.COUNTRIES_BASE_URL).build()
            .create(CountriesApiService::class.java)

    override suspend fun getCountry(currency: String): CountryEnvelope {
        return api.getCountry(currency)[0] //if its not the first on the list it has to be overriden
    }
}