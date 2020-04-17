package io.csqn.rates.domain.mocks

import io.csqn.rates.domain.CountriesRepositoryType
import io.csqn.rates.domain.factories.CountryFactory
import io.csqn.rates.domain.models.Country

class MockCountriesRepositoryType: CountriesRepositoryType {

    override suspend fun getCountryData(code: String): Country {
        return CountryFactory.create()
    }
}