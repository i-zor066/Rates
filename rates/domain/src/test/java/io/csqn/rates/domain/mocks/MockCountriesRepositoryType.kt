package io.csqn.rates.domain.mocks

import io.csqn.rates.domain.CountriesRepositoryType
import io.csqn.rates.domain.factories.CountryFactory
import io.csqn.rates.domain.models.Country
import io.reactivex.Single

class MockCountriesRepositoryType: CountriesRepositoryType {

    override fun getCountryData(code: String): Single<Country> {
        return Single.just(CountryFactory.create())
    }
}