package io.csqn.rates.data.repository

import io.csqn.rates.data.api.CountriesApiType
import io.csqn.rates.data.cache.CountriesCacheType
import io.csqn.rates.data.envelopes.CountryEnvelope
import io.csqn.rates.data.mappers.CountryMapper
import io.csqn.rates.data.mappers.CurrencyOverrides
import io.csqn.rates.domain.CountriesRepositoryType
import io.csqn.rates.domain.models.Country
import javax.inject.Inject


class CountriesRepository @Inject constructor(
    private val countriesApi: CountriesApiType,
    private val countriesCache: CountriesCacheType
) : CountriesRepositoryType {

    override suspend fun getCountryData(code: String): Country {
        val envelope = if (countriesCache.isCached(code)) {
            countriesCache.getCountryFromCache(code)
        } else {
            countriesCache.saveToCache(code, countriesApi.getCountry(code))
        }
        return CountryMapper.fromEnvelope(envelope)
    }
}
