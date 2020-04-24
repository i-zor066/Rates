package io.csqn.rates.data.repository

import io.csqn.rates.data.api.CountriesApiType
import io.csqn.rates.data.cache.CountriesCacheType
import io.csqn.rates.data.mappers.CountryMapper
import io.csqn.rates.domain.CountriesRepositoryType
import io.csqn.rates.domain.models.Country
import io.reactivex.Single
import javax.inject.Inject

class CountriesRepository @Inject constructor(
    private val countriesApi: CountriesApiType,
    private val countriesCache: CountriesCacheType
) : CountriesRepositoryType {

    override fun getCountryData(code: String): Single<Country> {
        val envelopeSingle = if (countriesCache.isCached(code)) {
            countriesCache.getCountryFromCache(code)
        } else {
            countriesApi.getCountry(code).flatMap { countriesCache.saveToCache(code, it )}
        }
        return envelopeSingle.map {CountryMapper.fromEnvelope(it)}
    }
}
