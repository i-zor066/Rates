package io.csqn.rates.presentation.mocks

import io.csqn.rates.domain.CountriesRepositoryType
import io.csqn.rates.domain.models.Country
import io.csqn.rates.presentation.factories.CountryFactory
import io.reactivex.Single

class MockCountriesRepositoryType : CountriesRepositoryType {

    override fun getCountryData(code: String): Single<Country> {
        return Single.just(CountryFactory.create())
    }
}
