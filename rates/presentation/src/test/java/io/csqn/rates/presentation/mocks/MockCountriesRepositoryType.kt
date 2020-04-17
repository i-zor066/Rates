package io.csqn.rates.presentation.mocks

import io.csqn.rates.domain.CountriesRepositoryType
import io.csqn.rates.presentation.factories.CountryFactory
import io.csqn.rates.domain.models.Country

class MockCountriesRepositoryType: CountriesRepositoryType {

    override suspend fun getCountryData(code: String): Country {
        return CountryFactory.create()
    }
}